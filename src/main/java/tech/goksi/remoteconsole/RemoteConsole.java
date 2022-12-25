package tech.goksi.remoteconsole;

import io.javalin.Javalin;
import io.javalin.http.ContentType;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import tech.goksi.remoteconsole.api.Routes;
import tech.goksi.remoteconsole.api.websocket.WebsocketHandler;
import tech.goksi.remoteconsole.helpers.GsonMapper;
import tech.goksi.remoteconsole.token.TokenStore;

public final class RemoteConsole extends JavaPlugin {
    private TokenStore tokenStore;
    private Javalin javalinApp;
    private WebsocketHandler websocketHandler;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupJavalin();
        tokenStore = new TokenStore();
        websocketHandler = new WebsocketHandler();
    }

    @Override
    public void onDisable() {

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
        javalinApp = Javalin.create(config -> {
            config.jsonMapper(new GsonMapper());
            config.defaultContentType = ContentType.JSON;
            config.showJavalinBanner = false;
            config.server(() -> {
                QueuedThreadPool pool = new QueuedThreadPool();
                pool.setName("RemoteConsolePool");
                return new Server(pool);
            });
        }).start(getConfig().getString("ConsoleConfiguration.Host"), getConfig().getInt("ConsoleConfiguration.Port"));
        new Routes();
    }
}
