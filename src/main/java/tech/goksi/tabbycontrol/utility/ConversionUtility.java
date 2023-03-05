package tech.goksi.tabbycontrol.utility;

public class ConversionUtility {
    private ConversionUtility() {
    }

    public static long minutesToTicks(int minutes) {
        return (long) minutes * secondsToTicks(60);
    }

    public static long secondsToTicks(int seconds) {
        return (long) seconds * 20;
    }

    public static int millisToMinutes(long millis) {
        return (int) (millis / 60000);
    }
}
