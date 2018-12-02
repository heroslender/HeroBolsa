package com.heroslender.herobolsa.placeholders;

import com.heroslender.herobolsa.HeroBolsa;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceholderApiHook extends EZPlaceholderHook {

    public PlaceholderApiHook() {
        super(HeroBolsa.getInstance(), "bolsa");
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return Integer.toString(HeroBolsa.getInstance().getValue());
    }
}
