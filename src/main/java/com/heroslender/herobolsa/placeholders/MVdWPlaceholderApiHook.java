package com.heroslender.herobolsa.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.heroslender.herobolsa.HeroBolsa;

public class MVdWPlaceholderApiHook {

    public void hook() {
        PlaceholderAPI.registerPlaceholder(HeroBolsa.getInstance(), "bolsa",
                placeholderReplaceEvent -> Integer.toString(HeroBolsa.getInstance().getValue())
        );
    }
}
