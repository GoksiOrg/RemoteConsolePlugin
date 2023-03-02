package tech.goksi.tabbycontrol.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.management.OperatingSystemMXBean;
import org.bukkit.Bukkit;
import tech.goksi.tabbycontrol.TabbyControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Base64;
import java.util.logging.Level;

public class ServerInfo {
    private static String SERVER_ICON;
    @Expose
    @SerializedName("total_ram")
    private final float totalRam;
    @Expose
    @SerializedName("used_ram")
    private final float usedRam;
    @Expose
    @SerializedName("cpu_usage")
    private final double cpuUsage;
    @Expose
    @SerializedName("online_players")
    private final int onlinePlayers;
    @Expose
    @SerializedName("max_players")
    private final int maxPlayers;
    @Expose
    @SerializedName("server_icon")
    private final String serverIcon;

    public ServerInfo() {
        if (SERVER_ICON == null) SERVER_ICON = getBase64(new File("server-icon.png"));
        this.serverIcon = SERVER_ICON;
        maxPlayers = Bukkit.getMaxPlayers();
        onlinePlayers = Bukkit.getOnlinePlayers().size();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        totalRam = (float) heapMemoryUsage.getMax() / 1024 / 1024;
        usedRam = (float) heapMemoryUsage.getUsed() / 1024 / 1024;
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        cpuUsage = operatingSystemMXBean.getProcessCpuLoad(); // TODO: hmm, dk about using com.sun
    }

    @SuppressWarnings("ResultOfMethodCallIgnored ")
    private static String getBase64(File file) {
        if (!file.exists()) return "";
        String result;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] fileByte = new byte[(int) file.length()];
            inputStream.read(fileByte);
            result = Base64.getEncoder().encodeToString(fileByte);
        } catch (IOException exception) {
            TabbyControl.getInstance().getLogger().log(Level.SEVERE, "Failed to get file input stream !", exception);
            return "";
        }
        return result;
    }

    public double getTotalRam() {
        return totalRam;
    }

    public double getUsedRam() {
        return usedRam;
    }

    public double getMaxCpuUsage() {
        return cpuUsage;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getServerIcon() {
        return serverIcon;
    }
}
