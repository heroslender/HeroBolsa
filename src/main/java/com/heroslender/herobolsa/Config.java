package com.heroslender.herobolsa;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Config {
    @Getter private final List<String> comandosAtualizarSubiu = new ArrayList<>();
    @Getter private final List<String> comandosAtualizarDesceu = new ArrayList<>();
    @Getter private final List<String> anuncioAtualizarSubiu = new ArrayList<>();
    @Getter private final List<String> anuncioAtualizarDesceu = new ArrayList<>();

    @Getter private final String mensagemBolsa;

    @Getter private final int max;
    @Getter private final int min;
    @Getter private final int diffMax;
    @Getter private final int diffMin;
    @Getter private final boolean forceLimit;

    @Getter private final int updateDelay;

    public Config(FileConfiguration config) {
        if (!config.contains("settings.valor.max"))
            config.set("settings.valor.max", 100);
        if (!config.contains("settings.valor.min"))
            config.set("settings.valor.min", 0);
        if (!config.contains("settings.diferenca.max"))
            config.set("settings.diferenca.max", 20);
        if (!config.contains("settings.diferenca.min"))
            config.set("settings.diferenca.min", -10);
        if (!config.contains("settings.forcar-limite"))
            config.set("settings.forcar-limite", true);

        if (!config.contains("settings.delay-atualizar"))
            config.set("settings.delay-atualizar", 60);

        if (!config.contains("mensagem.check-valor"))
            config.set("mensagem.check-valor", "&eValor atual da bolsa: &7:bolsa:%");
        if (!config.contains("mensagem.comandos-atualizar-subiu"))
            config.set("mensagem.comandos-atualizar-subiu", Arrays.asList("tm bc Valor da bolsa subiu!", "tm bc :)"));
        if (!config.contains("mensagem.comandos-atualizar-desceu"))
            config.set("mensagem.comandos-atualizar-desceu", Arrays.asList("tm bc Valor da bolsa desceu!", "tm bc :("));
        if (!config.contains("mensagem.anuncio-atualizar-subiu"))
            config.set("mensagem.anuncio-atualizar-subiu", Arrays.asList("&eBolsa atualizada para &7:bolsa:% &2▲ &a:diferenca:%!", "&a:)"));
        if (!config.contains("mensagem.anuncio-atualizar-desceu"))
            config.set("mensagem.anuncio-atualizar-desceu", Arrays.asList("&eBolsa atualizada para &7:bolsa:% &4▼ &c:diferenca:%!", ":("));
        HeroBolsa.getInstance().saveConfig();

        max = config.getInt("settings.valor.max", 100);
        min = config.getInt("settings.valor.min", 0);
        diffMax = config.getInt("settings.diferenca.max", 20);
        diffMin = config.getInt("settings.diferenca.min", 10);
        forceLimit = config.getBoolean("settings.forcar-limite", true);

        updateDelay = config.getInt("settings.delay-atualizar", 60) * 20;

        mensagemBolsa = translateColors(config.getString("mensagem.check-valor", "&eValor atual da bolsa: &7:bolsa:%"));

        comandosAtualizarSubiu.addAll(config.getStringList("mensagem.comandos-atualizar-subiu").stream().map(this::translateColors).collect(Collectors.toList()));
        comandosAtualizarDesceu.addAll(config.getStringList("mensagem.comandos-atualizar-desceu").stream().map(this::translateColors).collect(Collectors.toList()));
        anuncioAtualizarSubiu.addAll(config.getStringList("mensagem.anuncio-atualizar-subiu").stream().map(this::translateColors).collect(Collectors.toList()));
        anuncioAtualizarDesceu.addAll(config.getStringList("mensagem.anuncio-atualizar-desceu").stream().map(this::translateColors).collect(Collectors.toList()));
    }

    private String translateColors(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
