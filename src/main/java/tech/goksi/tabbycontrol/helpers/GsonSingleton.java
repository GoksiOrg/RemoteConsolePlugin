package tech.goksi.tabbycontrol.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSingleton {
    private static Gson gson;

    private GsonSingleton() {
    }

    public static Gson getInstance() {
        if (gson == null) gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson;
    }
}
