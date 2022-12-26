package tech.goksi.remoteconsole.token;

import org.bukkit.Bukkit;
import tech.goksi.remoteconsole.RemoteConsole;

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
        Bukkit.getScheduler().runTaskLaterAsynchronously(RemoteConsole.getInstance(), () -> tokens.remove(token), 60 * 60 * 20);
        return true;
    }
}
