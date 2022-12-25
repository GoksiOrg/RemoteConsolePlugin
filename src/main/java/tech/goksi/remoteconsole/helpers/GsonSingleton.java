package tech.goksi.remoteconsole.helpers;

import com.google.gson.Gson;

public class GsonSingleton {
    private static Gson gson;

    private GsonSingleton() {
    }

    public static Gson getInstance() {
        if (gson == null) gson = new Gson();
        return gson;
    }
}
