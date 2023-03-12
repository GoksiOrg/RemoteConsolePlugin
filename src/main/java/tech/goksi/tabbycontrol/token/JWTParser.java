package tech.goksi.tabbycontrol.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bukkit.configuration.file.FileConfiguration;
import tech.goksi.tabbycontrol.TabbyControl;


public class JWTParser {
    private static final JWTVerifier jwtVerifier;

    private JWTParser() {
    }

    static {
        FileConfiguration config = TabbyControl.getInstance().getConfig();
        jwtVerifier = JWT.require(Algorithm.HMAC256(config.getString("ConsoleConfiguration.Secret")))
                .withIssuer(config.getString("ConsoleConfiguration.RemoteConsoleURL"))
                .withClaim("server_id", config.getString("ConsoleConfiguration.ServerID"))
                .withClaimPresence("sub")
                .withClaimPresence("jti")
                .withClaimPresence("iat")
                .withClaimPresence("exp")
                .withClaimPresence("send_commands")
                .build();
    }

    public static DecodedJWT parse(String jwt) throws JWTVerificationException {
        DecodedJWT result = jwtVerifier.verify(jwt);
        if (!TabbyControl.getInstance().getTokenStore().isUniqueToken(result.getClaim("jti").asString()))
            throw new JWTVerificationException("Provided id is not unique !");
        return result;
    }

}
