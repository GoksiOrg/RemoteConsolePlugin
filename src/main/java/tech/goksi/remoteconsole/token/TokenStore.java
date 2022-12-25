package tech.goksi.remoteconsole.token;

import org.bukkit.Bukkit;
import tech.goksi.remoteconsole.RemoteConsole;

import java.util.ArrayList;
import java.util.List;

public class TokenStore {
    private final List<String> tokens;

    public TokenStore() {
        tokens = new ArrayList<>();
    }

    public boolean isUniqueToken(String token) {
        if (tokens.contains(token)) return false;
        tokens.add(token);
        Bukkit.getScheduler().runTaskLaterAsynchronously(RemoteConsole.getInstance(), () -> tokens.remove(token), 60 * 60 * 20);
        return true;
    }
}
