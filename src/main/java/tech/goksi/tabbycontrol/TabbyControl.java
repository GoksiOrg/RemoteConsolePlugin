package tech.goksi.tabbycontrol;

import io.javalin.Javalin;
import io.javalin.community.ssl.SSLPlugin;
import io.javalin.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.configuration.ConfigurationSection;
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

/*TODO: cors*/
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
        startWebserver();
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

    public WebsocketHandler getWebsocketHandler() {
        return websocketHandler;
    }

    private Javalin createJavalin() {
        int port = getConfig().getInt("ConsoleConfiguration.Port", 0);
        String host = getConfig().getString("ConsoleConfiguration.Host", "0.0.0.0");
        if (port == 0) {
            getLogger().warning("Webserver didn't start, awaiting configuration command...");
            return null;
        }
        Javalin javalinApp = Javalin.create(config -> {
            config.jsonMapper(new GsonMapper());
            config.http.defaultContentType = ContentType.JSON;
            config.showJavalinBanner = false;
            config.jetty.server(() -> {
                QueuedThreadPool pool = new QueuedThreadPool();
                pool.setName("TabbyPool");
                return new Server(pool);
            });
            if (SSL_ENABLED) {
                ConfigurationSection sslSection = getConfig().getConfigurationSection("ConsoleConfiguration.SSL");
                SSLPlugin sslPlugin = new SSLPlugin(sslConfig -> {
                    sslConfig.insecure = false;
                    sslConfig.securePort = port;
                    sslConfig.host = host;
                    sslConfig.http2 = true;
                    String pluginDir = getDataFolder().getPath();
                    sslConfig.pemFromPath(pluginDir + sslSection.getString("CertPath"),
                            pluginDir + sslSection.getString("KeyPath"));
                });

                config.plugins.register(sslPlugin);
            }
        }).start(host, port);
        javalinApp.wsException(WebSocketException.class, (exception, ctx) -> ctx.send(exception));
        javalinApp.exception(RestException.class, ((exception, ctx) -> ctx.json(exception).status(exception.getStatus())));
        new Routes(javalinApp);
        return javalinApp;
    }

    public void shutdownWebserver() {
        if (javalinApp == null) return;
        javalinApp.close();
        javalinApp = null;
    }

    public void startWebserver() {
        if (javalinApp != null) return;
        javalinApp = createJavalin();
    }
}
