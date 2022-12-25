package tech.goksi.remoteconsole.utility;

import org.bukkit.ChatColor;
import tech.goksi.remoteconsole.RemoteConsole;

import java.security.SecureRandom;

public class StringUtility {
    private StringUtility() {
    }

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&";

    public static String randomString(int amount) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(amount);

        for (int i = 0; i < amount; i++) {
            stringBuilder.append(CHARS.charAt(secureRandom.nextInt(CHARS.length())));
        }

        return stringBuilder.toString();
    }

    public static String randomString() {
        return randomString(6);
    }

    public static String getColoredString(String path) {
        return ChatColor.translateAlternateColorCodes('&', RemoteConsole.getInstance().getConfig().getString(path));
    }
}
