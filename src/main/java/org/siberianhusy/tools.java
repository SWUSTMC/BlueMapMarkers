package org.siberianhusy;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.gson.MarkerGson;
import de.bluecolored.bluemap.api.markers.Marker;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static org.siberianhusy.BlueMapAPI.*;


public class tools {
    public static void saveWorldMarkerSet(World world) {
        String name = "marker-set-" + world.getName() + ".json";
        File file = new File(BlueMapMarkers.bmm.getDataFolder(), name);
        try (FileWriter writer = new FileWriter(file)) {
            MarkerGson.INSTANCE.toJson(markerSet.get(world), writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void createFiles() {
        for (World world : Bukkit.getWorlds()) {
            String name = "marker-set-" + world.getName() + ".json";
            File file = new File(BlueMapMarkers.bmm.getDataFolder(), name);
            try {
                File folder = BlueMapMarkers.bmm.getDataFolder();
                if (!folder.exists()) folder.mkdirs();
                if (!file.exists()) file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void loadWorldMarkerSet(World world) {
        String name = "marker-set-" + world.getName() + ".json";
        File file = new File(BlueMapMarkers.bmm.getDataFolder(), name);
        try (FileReader reader = new FileReader(file)) {
            MarkerSet set = MarkerGson.INSTANCE.fromJson(reader, MarkerSet.class);
            if (set != null) markerSet.put(world, set);
        } catch (FileNotFoundException ignored) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void registerWorld(World world) {
        de.bluecolored.bluemap.api.BlueMapAPI.onEnable(api ->
                api.getWorld(world).ifPresent(blueWorld -> {
                    for (BlueMapMap map : blueWorld.getMaps()) {
                        String label = world.getName()+"-长期";
                        String linShiLabel =  world.getName()+"-临时";
                        MarkerSet set = markerSet.get(world);
                        MarkerSet linShi = linShiMarkerSet.get(world);
                        if (set == null) set = MarkerSet.builder().label(label).build();
                        if (linShi == null) linShi = MarkerSet.builder().label(linShiLabel).build();
                        map.getMarkerSets().put(label, set);
                        map.getMarkerSets().put(linShiLabel,linShi);
                        markerSet.put(world, set);
                        linShiMarkerSet.put(world,linShi);
                    }
                })
        );
    }
    //添加临时标记
    public static void linShiAddMarker(Player player, String name){
        Vector3d pos = new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        String id = "marker-" + pos.getX() + "-" + pos.getY() + "-" + pos.getZ();
        POIMarker marker = POIMarker.builder().label(name).position(pos).maxDistance(100000).build();
        BlueMapAPI.linShiMarkerSet.get(player.getWorld()).put(id, marker);
    }
    public static void linShiAddMarker(Player player, String name,String icon){
        Vector2i anchor;
        BufferedImage image = getBufferedImage(icon);
        int width = image.getWidth();
        int height = image.getHeight();
        anchor = new Vector2i(height/2, width/2);
        Vector3d pos = new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        String id = "marker-" + pos.getX() + "-" + pos.getY() + "-" + pos.getZ();
        POIMarker marker = POIMarker.builder().label(name).position(pos).icon(icon,anchor).maxDistance(100000).build();
        BlueMapAPI.linShiMarkerSet.get(player.getWorld()).put(id, marker);
    }
    //添加长期标记
    //icon为 链接
    public static void addMarker(Player player, String name, String icon){
        Vector2i anchor;
        BufferedImage image = getBufferedImage(icon);
        int width = image.getWidth();
        int height = image.getHeight();
        anchor = new Vector2i(height/2, width/2);
        Vector3d pos = new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        String id = "marker-" + pos.getX() + "-" + pos.getY() + "-" + pos.getZ();
        POIMarker marker = POIMarker.builder().label(name).position(pos).maxDistance(100000).icon(icon,anchor).build();
        BlueMapAPI.markerSet.get(player.getWorld()).put(id, marker);
    }
    public static String replace(String string,String name){
        return string.replace("&","§").replace("[name]",name);
    }
    public static String replace(String string){
        return string.replace("&","§");
    }

    //获取在线图片
    public static BufferedImage getBufferedImage(String imgUrl) {
        URL url = null;
        InputStream is = null;
        BufferedImage img = null;
        try {
            url = new URL(imgUrl);
            is = url.openStream();
            img = ImageIO.read(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
    //创建GUI
    public static void invCreate(){
        for (Map.Entry<Integer,World> entry:GUI.ID.entrySet()){
            World world = entry.getValue();
            Inventory inventory = GUI.worldMarkers.get(world);
            ItemStack mar = new ItemStack(Material.NAME_TAG);
            int count = 0;
            for (Map.Entry<String, Marker> entry1 : markerSet.get(world).getMarkers().entrySet()){
                ID.put(count,entry1.getKey());
                Vector3d pos = entry1.getValue().getPosition();
                ItemMeta itemMeta = mar.getItemMeta();
                itemMeta.setDisplayName(ChatColor.BLUE +entry1.getValue().getLabel());
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.DARK_AQUA +"X："+ChatColor.AQUA+(int)pos.getX());
                lore.add(ChatColor.DARK_AQUA +"Y："+ChatColor.AQUA+(int)pos.getY());
                lore.add(ChatColor.DARK_AQUA +"Z："+ChatColor.AQUA+(int)pos.getZ());
                lore.add(ChatColor.RED+"单击删除标记点");
                itemMeta.setLore(lore);
                mar.setItemMeta(itemMeta);
                inventory.setItem(count,mar);
                count++;
            }
        }
    }
}
