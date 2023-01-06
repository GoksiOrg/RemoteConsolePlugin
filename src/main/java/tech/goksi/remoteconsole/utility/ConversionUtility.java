package tech.goksi.remoteconsole.utility;

public class ConversionUtility {
    private ConversionUtility() {
    }

    public static long minutesToTicks(int minutes) {
        return (long) minutes * 60 * 20;
    }

    public static int millisToMinutes(long millis) {
        return (int) (millis / 60000);
    }
}
