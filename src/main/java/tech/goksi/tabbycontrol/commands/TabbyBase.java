package tech.goksi.tabbycontrol.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import tech.goksi.tabbycontrol.TabbyControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.goksi.tabbycontrol.utility.CommonUtility.sendMessage;

public class TabbyBase implements TabExecutor {
    private final Map<String, CommandHandler> handlerMap;

    public TabbyBase() {
        handlerMap = new HashMap<>();
        setupHandlers();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*TODO: check args length*/
        CommandHandler handler = handlerMap.get(args[0]);
        if (handler == null) {
            /*TODO: unknown subcommand*/
        } else handler.handle(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    private void setupHandlers() {
        handlerMap.put("setup", (sender, args) -> {
            if (!(sender instanceof ConsoleCommandSender)) {
                sendMessage(sender, "OnlyConsole");
                return;
            }
            if (args.length != 5) {
                sendMessage(sender, "WrongUsage");
                return;
            }
            FileConfiguration config = TabbyControl.getInstance().getConfig();
            config.set("ConsoleConfiguration.ID", args[1]);
            config.set("ConsoleConfiguration.Port", Integer.parseInt(args[2]));
            config.set("ConsoleConfiguration.Remote", args[3]);
            config.set("ConsoleConfiguration.Secret", args[4]);
            TabbyControl.getInstance().saveConfig();
            sendMessage(sender, "SuccessSetup");
        });
    }
}
