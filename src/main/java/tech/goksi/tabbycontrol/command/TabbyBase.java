package tech.goksi.tabbycontrol.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import tech.goksi.tabbycontrol.command.subcommands.TabbySetup;
import tech.goksi.tabbycontrol.command.subcommands.TabbyStop;

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
            sendMessage(sender, "UnknownSubcommand");
        } else handler.handle(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    private void setupHandlers() {
        handlerMap.put("setup", new TabbySetup());
        handlerMap.put("stop", new TabbyStop());
        handlerMap.put("start", (sender, args) -> {

        });
    }
}
