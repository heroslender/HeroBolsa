package com.heroslender.herobolsa.command;

import com.heroslender.herobolsa.Config;
import com.heroslender.herobolsa.HeroBolsa;
import com.heroslender.herovender.helpers.MessageBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BolsaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && sender.hasPermission("herovender.staff")) {
            if (args[0].equalsIgnoreCase("reload")) {
                HeroBolsa.getInstance().reloadConfig();
                HeroBolsa.getInstance().setConfiguration(new Config(HeroBolsa.getInstance().getConfig()));
                sender.sendMessage("Config reiniciada com sucesso!");
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length > 1) {
                    int valor;
                    try {
                        valor = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Esse não é um valor válido!");
                        return true;
                    }

                    HeroBolsa.getInstance().setValue(valor);
                    sender.sendMessage("§aValor da bolsa atualizado para " + valor + "%!");
                    return true;
                }
                sender.sendMessage("§cUso correto: §7/" + label + " set <valor>");
                return true;
            }
        }
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.withPlaceholder(sender);
        messageBuilder.withPlaceholder("bolsa", HeroBolsa.getInstance().getValue());

        sender.sendMessage(messageBuilder.build(HeroBolsa.getInstance().getConfiguration().getMensagemBolsa()));
        return true;
    }
}
