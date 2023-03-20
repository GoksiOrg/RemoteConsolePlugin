package tech.goksi.tabbycontrol.helpers;

import com.google.gson.Gson;
import io.javalin.json.JsonMapper;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class GsonMapper implements JsonMapper {
    private final Gson gson;

    public GsonMapper() {
        gson = GsonSingleton.getInstance();
    }

    @NotNull
    @Override
    public <T> T fromJsonStream(@NotNull InputStream json, @NotNull Type targetType) {
        return gson.fromJson(new InputStreamReader(json), targetType);
    }

    @NotNull
    @Override
    public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
        return gson.fromJson(json, targetType);
    }

    @NotNull
    @Override
    public InputStream toJsonStream(@NotNull Object obj, @NotNull Type type) {
        return new ByteArrayInputStream(toJsonString(obj, type).getBytes(StandardCharsets.UTF_8));
    }

    @NotNull
    @Override
    public String toJsonString(@NotNull Object obj, @NotNull Type type) {
        return gson.toJson(obj, type);
    }
}
