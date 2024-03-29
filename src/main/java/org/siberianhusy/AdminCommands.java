package org.siberianhusy;

import com.flowpowered.math.vector.Vector3d;
import de.bluecolored.bluemap.api.markers.Marker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


import java.util.HashMap;
import java.util.Map;


public class AdminCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length==0||args.length==1&&args[0].equalsIgnoreCase("help")){
            sender.sendMessage("§3============§bBlueMapMarkers§3============\n"
                    +"§b/bmma help         §9管理员命令帮助\n"
                    +"§b/bmma reload       §9重载插件");
            return true;
        }
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            sender.sendMessage("§9BlueMapMarkets正在重载！");
            BlueMapMarkers.bmm.reloadConfig();
            sender.sendMessage("§9BlueMapMarkets重载完成！");
            return true;
        }

        sender.sendMessage(ChatColor.RED+"/bmma help");
        return true;
    }

}

