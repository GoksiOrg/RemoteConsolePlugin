package tech.goksi.tabbycontrol.command;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandHandler {
    void handle(CommandSender sender, String[] args);
}
