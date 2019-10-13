package com.heroslender.herobolsa.placeholders;

import com.heroslender.herobolsa.HeroBolsa;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderApiHook extends PlaceholderExpansion {
    private final HeroBolsa heroBolsa;

    public PlaceholderApiHook() {
        this.heroBolsa = HeroBolsa.getInstance();
    }

    @Override
    public String getIdentifier() {
        return "bolsa";
    }

    @Override
    public String getAuthor() {
        return heroBolsa.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return heroBolsa.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return Integer.toString(heroBolsa.getValue());
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }
}
