package tech.goksi.tabbycontrol.commands;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandHandler {
    void handle(CommandSender sender, String[] args);
}
