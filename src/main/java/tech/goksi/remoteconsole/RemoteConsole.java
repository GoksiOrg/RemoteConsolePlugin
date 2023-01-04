package tech.goksi.remoteconsole;

import io.javalin.Javalin;
import io.javalin.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import tech.goksi.remoteconsole.api.Routes;
import tech.goksi.remoteconsole.api.websocket.WebsocketHandler;
import tech.goksi.remoteconsole.events.ConsoleListener;
import tech.goksi.remoteconsole.helpers.GsonMapper;
import tech.goksi.remoteconsole.token.TokenStore;
import tech.goksi.remoteconsole.utility.versioncontrol.VersionControlUtility;
public final class RemoteConsole extends JavaPlugin {
    private TokenStore tokenStore;
    private Javalin javalinApp;
    private WebsocketHandler websocketHandler;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(RemoteConsole.class.getClassLoader());
        setupJavalin();
        Thread.currentThread().setContextClassLoader(classLoader);
        tokenStore = new TokenStore();
        websocketHandler = new WebsocketHandler();
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addFilter(new ConsoleListener());
        VersionControlUtility.checkVersion();
    }

    @Override
    public void onDisable() {
        javalinApp.close();
    }

    public static RemoteConsole getInstance() {
        return getPlugin(RemoteConsole.class);
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public Javalin getJavalinApp() {
        return javalinApp;
    }

    public WebsocketHandler getWebsocketHandler() {
        return websocketHandler;
    }

    private void setupJavalin() {
        int port = getConfig().getInt("ConsoleConfiguration.Port");
        if(port == 0) {
            getLogger().warning("Webserver didn't start, awaiting configuration command...");
            return;
        }
        javalinApp = Javalin.create(config -> {
            config.jsonMapper(new GsonMapper());
            config.defaultContentType = ContentType.JSON;
            config.showJavalinBanner = false;
            config.server(() -> {
                QueuedThreadPool pool = new QueuedThreadPool();
                pool.setName("RemoteConsolePool");
                return new Server(pool);
            });
        }).start(getConfig().getString("ConsoleConfiguration.Host"), port);
        new Routes();
    }
}
