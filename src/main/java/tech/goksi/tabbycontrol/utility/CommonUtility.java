package tech.goksi.tabbycontrol.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tech.goksi.tabbycontrol.TabbyControl;

import java.util.HashMap;
import java.util.Map;

public class CommonUtility {

    private CommonUtility() {
    }

    public static Map<String, Object> mapOf(Object... objects) {
        Map<String, Object> map = new HashMap<>();
        if (objects.length % 2 != 0) throw new IllegalArgumentException("Must have even number of arguments");
        for (int i = 0; i < objects.length; i += 2) {
            if (!(objects[i] instanceof String)) throw new IllegalArgumentException("Key must be string !");
            map.put((String) objects[i], objects[i + 1]);
        }
        return map;
    }

    public static void sendMessage(CommandSender sender, String path) {
        sender.sendMessage(colorize(TabbyControl.getInstance().getConfig().getString("Messages." + path)));
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
