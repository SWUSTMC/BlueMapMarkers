package org.siberianhusy;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class MainGUIEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("地图列表")){
            e.setCancelled(true);
            Player player = (Player)e.getWhoClicked();
            if (GUI.ID.containsKey(e.getRawSlot())){
                World world = GUI.ID.get(e.getRawSlot());
                Inventory inventory = GUI.worldMarkers.get(world);
                player.openInventory(inventory);
            }
        }
    }
    @EventHandler
    public void markerEvent(InventoryClickEvent e){
        for (Map.Entry<World,Inventory> entry:GUI.worldMarkers.entrySet()){
            if (e.getWhoClicked().getOpenInventory().getTitle().equals(entry.getKey().getName()+"-标记")){
                e.setCancelled(true);
                Player player = (Player)e.getWhoClicked();
                if (BlueMapAPI.ID.containsKey(e.getRawSlot())){
                    BlueMapAPI.markerSet.get(entry.getKey()).getMarkers().remove(BlueMapAPI.ID.get(e.getRawSlot()));
                    player.sendMessage(ChatColor.RED+"标记已删除!");
                    GUI.openGUI(player);
                    player.openInventory(entry.getValue());
                }
            }
        }
    }
}

