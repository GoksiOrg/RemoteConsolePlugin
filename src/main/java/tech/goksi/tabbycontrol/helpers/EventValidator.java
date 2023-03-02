package tech.goksi.tabbycontrol.helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import tech.goksi.tabbycontrol.api.exceptions.WsValidationException;

public class EventValidator {

    public static JsonObject validate(String message) {
        JsonObject eventObject;
        try {
            eventObject = GsonSingleton.getInstance().fromJson(message, JsonObject.class);
        } catch (JsonSyntaxException exception) {
            throw new WsValidationException("Provided input is not an valid json string");
        }
        JsonElement eventNameElement = eventObject.get("event");
        if (eventNameElement == null) {
            throw new WsValidationException("Invalid format, event field missing");
        }
        JsonElement dataElement = eventObject.get("data");
        if (dataElement == null || !dataElement.isJsonArray()) {
            throw new WsValidationException("Invalid format, data field must be array and not null");
        }
        return eventObject;
    }
}
