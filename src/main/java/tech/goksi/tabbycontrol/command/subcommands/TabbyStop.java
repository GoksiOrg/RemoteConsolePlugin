package tech.goksi.tabbycontrol.command.subcommands;

import io.javalin.Javalin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.goksi.tabbycontrol.TabbyControl;
import tech.goksi.tabbycontrol.command.CommandHandler;

import static tech.goksi.tabbycontrol.utility.CommonUtility.sendMessage;

public class TabbyStop implements CommandHandler {
    @Override
    public void handle(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                sendMessage(sender, "OnlyOP");
                return;
            }
        } else if (!(sender instanceof ConsoleCommandSender)) return;
        Javalin app = TabbyControl.getInstance().getJavalinApp();
        if (app != null) app.close();
        sendMessage(sender, "SuccessStop");
    }
}
