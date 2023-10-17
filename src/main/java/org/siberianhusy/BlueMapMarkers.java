package org.siberianhusy;

import de.bluecolored.bluemap.api.BlueMapAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import static org.siberianhusy.tools.*;


public final class BlueMapMarkers extends JavaPlugin {
    static BlueMapMarkers bmm;
    @Override
    public void onEnable() {
        bmm = this;
        saveDefaultConfig();
        createFiles();
        for (World world : Bukkit.getWorlds()) {
            loadWorldMarkerSet(world);
            registerWorld(world);
        }
        BlueMapAPI.onEnable(api -> org.siberianhusy.BlueMapAPI.webRoot = api.getWebApp().getWebRoot());
        //注册指令
        Bukkit.getPluginCommand("BlueMapMarkers").setExecutor(new Commands());
        Bukkit.getPluginCommand("BlueMapMarkersAdmin").setExecutor(new AdminCommands());
        Bukkit.getPluginCommand("bmgui").setExecutor(new GUI());
        Bukkit.getPluginManager().registerEvents(new MainGUIEvent(),this);
        this.getLogger().info("BlueMapMarkers加载完成！欢迎使用！");
    }

    @Override
    public void onDisable() {
        for (World world : Bukkit.getWorlds()) saveWorldMarkerSet(world);
        this.getLogger().info("BlueMapMarkers已卸载！感谢使用！");
    }
}
