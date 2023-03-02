package tech.goksi.tabbycontrol.token;

import org.bukkit.Bukkit;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.utility.ConversionUtility;

import java.util.HashSet;
import java.util.Set;

public class TokenStore {
    private final Set<String> tokens;

    public TokenStore() {
        tokens = new HashSet<>();
    }

    public boolean isUniqueToken(String token) {
        if (tokens.contains(token)) return false;
        tokens.add(token);
        Bukkit.getScheduler().runTaskLaterAsynchronously(TabbyControl.getInstance(), () -> tokens.remove(token), ConversionUtility.minutesToTicks(45));
        return true;
    }
}
