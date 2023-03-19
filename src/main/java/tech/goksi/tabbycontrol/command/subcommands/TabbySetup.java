package tech.goksi.tabbycontrol.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.command.CommandHandler;

import static tech.goksi.tabbycontrol.utility.CommonUtility.sendMessage;

public class TabbySetup implements CommandHandler {
    @Override
    public void handle(CommandSender sender, String[] args) {
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
    }
}
