package tech.goksi.remoteconsole.utility.versioncontrol;

import java.util.Arrays;
import java.util.List;

public class SemVer implements Comparable<SemVer> {
    private final String version;

    public SemVer(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int compareTo(SemVer other) {
        int result = 0;
        List<String> versionOne = Arrays.asList(version.replaceAll("v", "").split("\\."));
        List<String> versionTwo = Arrays.asList(other.getVersion().replaceAll("v", "").split("\\."));

        for (int i = 0; i < 3; i++) {
            Integer v1 = Integer.parseInt(versionOne.get(i));
            Integer v2 = Integer.parseInt(versionTwo.get(i));
            int compare = v1.compareTo(v2);
            if (compare != 0) {
                result = compare;
                break;
            }
        }
        return result;
    }
}
