package org.siberianhusy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class GUI implements CommandExecutor {
    public static Map<World,Inventory> worldMarkers = new HashMap<>();
    public static Map<Integer, World> ID = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)){
            sender.sendMessage(tools.replace(Objects.requireNonNull(BlueMapMarkers.bmm.getConfig().getString("Error.sender"))));
        }else {
            Player player = (Player)sender;
            player.openInventory(openGUI(player));
        }
        return false;
    }
    public static Inventory openGUI(Player player){
        Inventory mainGUI = Bukkit.createInventory(player,9,"地图列表");
        ItemStack worldList = new ItemStack(Material.GRASS_BLOCK);
        int count = 0;
        for (World world : Bukkit.getWorlds()) {
            ItemMeta itemMeta = worldList.getItemMeta();
            itemMeta.setDisplayName(world.getName());
            worldList.setItemMeta(itemMeta);
            mainGUI.setItem(count,worldList);
            Inventory inventory = Bukkit.createInventory(player,6*9,world.getName()+"-标记");
            worldMarkers.put(world,inventory);
            ID.put(count,world);
            count++;
        }
        tools.invCreate();
        return mainGUI;
    }
}
