package tech.goksi.tabbycontrol.utility;

import org.eclipse.jetty.http.HttpFieldPreEncoder;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.http2.hpack.HpackFieldPreEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectionUtility {
    private ReflectionUtility() {
    }

    public static void injectHpackPreEncoder() throws NoSuchFieldException, IllegalAccessException {
        Field encodersField = PreEncodedHttpField.class.getDeclaredField("__encoders");
        encodersField.setAccessible(true);
        HttpFieldPreEncoder[] encoders = (HttpFieldPreEncoder[]) encodersField.get(null);
        HttpFieldPreEncoder[] newEncoders = Arrays.copyOf(encoders, encoders.length + 1);
        newEncoders[encoders.length] = new HpackFieldPreEncoder();
        encodersField.set(null, newEncoders);
    }
}
