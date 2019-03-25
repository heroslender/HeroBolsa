package com.heroslender.herobolsa;

import com.heroslender.herobolsa.command.BolsaCommand;
import com.heroslender.herobolsa.placeholders.MVdWPlaceholderApiHook;
import com.heroslender.herobolsa.placeholders.PlaceholderApiHook;
import com.heroslender.herovender.data.SellBonus;
import com.heroslender.herovender.event.PlayerSellEvent;
import com.heroslender.herovender.helpers.MessageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.SplittableRandom;

public final class HeroBolsa extends JavaPlugin {
    private static final SplittableRandom RANDOM = new SplittableRandom();
    @Getter private static HeroBolsa instance;
    @Getter @Setter private Config configuration;
    /**
     * Valor atual da bolsa
     */
    @Getter @Setter private int value = 1;
    private int oldValue = 0;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        configuration = new Config(getConfig());

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            private void onPlayerSell(final PlayerSellEvent e) {
                e.getInvoice().getBonuses().add(new SellBonus("Bolsa", getValue() / 100D));
            }
        }, this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::updateBolsa, 200, configuration.getUpdateDelay());

        getCommand("bolsa").setExecutor(new BolsaCommand());

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PlaceholderApiHook().hook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")){
            new MVdWPlaceholderApiHook().hook();
        }

        new Metrics(this);
    }

    private void updateBolsa() {
        var increse = value > oldValue;

        if (increse && value >= configuration.getMax())
            increse = false;
        else if (!increse && value <= configuration.getMin()) {
            increse = true;
        }

        int increaseValue = increse ?
                RANDOM.nextInt(configuration.getDiffMin(), configuration.getDiffMax()) :
                RANDOM.nextInt(-configuration.getDiffMax(), -configuration.getDiffMin());

        if (configuration.isForceLimit()) {
            val newValue = value + increaseValue;
            if (newValue > configuration.getMax()) {
                increaseValue = configuration.getMax() - value;
            } else if (newValue < configuration.getMin()) {
                increaseValue = configuration.getMin() - value;
            }
        }

        if (increaseValue == 0) {
            updateBolsa();
            return;
        }

        oldValue = value;
        value += increaseValue;

        val messageBuilder = new MessageBuilder();
        messageBuilder.withPlaceholder("bolsa", getValue());
        messageBuilder.withPlaceholder("bolsa-antiga", oldValue);
        messageBuilder.withPlaceholder("diferenca", increaseValue);


        runCommands(value > oldValue, messageBuilder);
    }

    private void runCommands(final boolean subiu, final MessageBuilder messageBuilder) {
        // Executar comandos sync
        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> runCommands(subiu, messageBuilder));
            return;
        }

        if (subiu) {
            configuration.getAnuncioAtualizarSubiu().forEach(message -> Bukkit.broadcastMessage(messageBuilder.build(message)));
            configuration.getComandosAtualizarSubiu().forEach(message -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), messageBuilder.build(message)));
        } else {
            configuration.getAnuncioAtualizarDesceu().forEach(message -> Bukkit.broadcastMessage(messageBuilder.build(message)));
            configuration.getComandosAtualizarDesceu().forEach(message -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), messageBuilder.build(message)));
        }
    }
}
