package org.siberianhusy;

import de.bluecolored.bluemap.api.markers.MarkerSet;
import org.bukkit.World;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlueMapAPI {
    public static Path webRoot;

    public static Map<World, MarkerSet> markerSet = new ConcurrentHashMap<>();


    public static Map<World, MarkerSet> linShiMarkerSet = new ConcurrentHashMap<>();

    public static Map<Integer, String> ID = new HashMap<>();



}
