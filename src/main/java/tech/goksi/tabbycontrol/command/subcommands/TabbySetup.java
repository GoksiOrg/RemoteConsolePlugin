package tech.goksi.tabbycontrol.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.command.CommandHandler;
import tech.goksi.tabbycontrol.utility.ConversionUtility;

import static tech.goksi.tabbycontrol.utility.CommonUtility.sendMessage;

public class TabbySetup implements CommandHandler {
    @Override
    public void handle(CommandSender sender, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sendMessage(sender, "OnlyConsole");
            return;
        }
        if (args.length != 6) {
            sendMessage(sender, "WrongUsage");
            return;
        }
        FileConfiguration config = TabbyControl.getInstance().getConfig();
        config.set("ConsoleConfiguration.ID", args[1]);
        config.set("ConsoleConfiguration.Port", Integer.parseInt(args[2]));
        config.set("ConsoleConfiguration.Remote", args[3]);
        config.set("ConsoleConfiguration.SSL.Enabled", Boolean.valueOf(args[4]));
        config.set("ConsoleConfiguration.Secret", args[5]);
        TabbyControl.getInstance().saveConfig();
        sendMessage(sender, "SuccessSetup");
        Bukkit.getScheduler()
                .runTaskLater(TabbyControl.getInstance(), Bukkit.spigot()::restart, ConversionUtility.secondsToTicks(3));
    }
}
