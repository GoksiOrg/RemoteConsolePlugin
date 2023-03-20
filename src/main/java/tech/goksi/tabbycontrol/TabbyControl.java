package tech.goksi.tabbycontrol;

import io.javalin.Javalin;
import io.javalin.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import tech.goksi.tabbycontrol.api.Routes;
import tech.goksi.tabbycontrol.api.exceptions.RestException;
import tech.goksi.tabbycontrol.api.exceptions.WebSocketException;
import tech.goksi.tabbycontrol.api.websocket.WebsocketHandler;
import tech.goksi.tabbycontrol.command.TabbyBase;
import tech.goksi.tabbycontrol.events.ConsoleListener;
import tech.goksi.tabbycontrol.helpers.GsonMapper;
import tech.goksi.tabbycontrol.token.TokenStore;
import tech.goksi.tabbycontrol.utility.versioncontrol.VersionControlUtility;

/*TODO: cors, ssl*/
public final class TabbyControl extends JavaPlugin {
    private TokenStore tokenStore;
    private Javalin javalinApp;
    private WebsocketHandler websocketHandler;
    private static boolean SSL_ENABLED;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        SSL_ENABLED = getConfig().getBoolean("ConsoleConfiguration.SSL.Enabled");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(TabbyControl.class.getClassLoader());
        setupJavalin();
        Thread.currentThread().setContextClassLoader(classLoader);
        tokenStore = new TokenStore();
        websocketHandler = new WebsocketHandler();
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addFilter(new ConsoleListener());
        getCommand("tabby").setExecutor(new TabbyBase());
        VersionControlUtility.checkVersion();
    }

    @Override
    public void onDisable() {
        if (javalinApp != null) javalinApp.close();
    }

    public static TabbyControl getInstance() {
        return getPlugin(TabbyControl.class);
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
        if (port == 0) {
            getLogger().warning("Webserver didn't start, awaiting configuration command...");
            return;
        }
        /*TODO: this will not work, have to put ssl trough jetty*/
        javalinApp = Javalin.create(config -> {
            config.jsonMapper(new GsonMapper());
            config.http.defaultContentType = ContentType.JSON;
            config.showJavalinBanner = false;
            config.jetty.server(() -> {
                QueuedThreadPool pool = new QueuedThreadPool();
                pool.setName("TabbyPool");
                return new Server(pool);
            });
        }).start(getConfig().getString("ConsoleConfiguration.Host"), port);
        javalinApp.wsException(WebSocketException.class, (exception, ctx) -> ctx.send(exception));
        javalinApp.exception(RestException.class, ((exception, ctx) -> ctx.json(exception).status(exception.getStatus())));
        new Routes();
    }
}
