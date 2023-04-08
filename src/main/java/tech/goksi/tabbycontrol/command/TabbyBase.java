package tech.goksi.tabbycontrol.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import tech.goksi.tabbycontrol.command.subcommands.TabbySetup;
import tech.goksi.tabbycontrol.command.subcommands.TabbyStop;

import java.util.*;

import static tech.goksi.tabbycontrol.utility.CommonUtility.sendMessage;

public class TabbyBase implements TabExecutor {
    private final Map<String, CommandHandler> handlerMap;
    private final Set<String> subcommands;

    public TabbyBase() {
        handlerMap = new HashMap<>();
        setupHandlers();
        subcommands = handlerMap.keySet();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        /*TODO: check args length*/
        CommandHandler handler = handlerMap.get(args[0]);
        if (handler == null) {
            sendMessage(sender, "UnknownSubcommand");
        } else handler.handle(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subcommands, completions);
        }
        return completions;
    }

    private void setupHandlers() {
        handlerMap.put("setup", new TabbySetup());
        handlerMap.put("stop", new TabbyStop());
        handlerMap.put("start", (sender, args) -> {

        });
    }
}
