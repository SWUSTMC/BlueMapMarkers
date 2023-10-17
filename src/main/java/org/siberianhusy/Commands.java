package org.siberianhusy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Objects;


public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


        if (args.length==0||(args.length==1&&args[0].equalsIgnoreCase("help"))){
            sender.sendMessage("§3============§bBlueMapMarkers§3============\n"
                    +"§b/bmm help          §9插件帮助\n"
                    +"§b/bmgui             §9打开标记点GUI\n"
                    +"§b/bmm add           §9添加临时点(关服时不会保存)\n"
                    +"§b/bmm add name      §9添加一个加name的临时标记点\n"
                    +"§b/bmm add name icon §9添加一个持久标记点\n"
                    +"§b/bmm icon          §9icon帮助");
            return true;
        }
        if (args.length>0&&args[0].equalsIgnoreCase("add")){
            if (!(sender instanceof Player)) {
                sender.sendMessage(tools.replace(Objects.requireNonNull(BlueMapMarkers.bmm.getConfig().getString("Error.sender"))));
                return true;
            }
            else{
                Player player = (Player) sender;
                String playerName = player.getName();
                if (args.length==1){
                    tools.linShiAddMarker(player,playerName);
                    sender.sendMessage(tools.replace(Objects.requireNonNull(BlueMapMarkers.bmm.getConfig().getString("add")),playerName));
                    return true;
                }else if (args.length==2){
                    tools.linShiAddMarker(player,args[1]);
                    sender.sendMessage(tools.replace(Objects.requireNonNull(BlueMapMarkers.bmm.getConfig().getString("add")),args[1]));
                    return true;
                }else if (args.length==3){
                    tools.addMarker(player,args[1],args[2]);
                    sender.sendMessage(tools.replace(Objects.requireNonNull(BlueMapMarkers.bmm.getConfig().getString("add")),args[1]));
                    return true;
                }else {
                    String Error = "&c用法：/bmm add [name] [icon]";
                    sender.sendMessage(tools.replace(Error));
                    return true;
                }
            }
        }
        if (args.length==1&&args[0].equalsIgnoreCase("icon")){
            List<String> iconHelp = BlueMapMarkers.bmm.getConfig().getStringList("icon");
            for (int i = 0; i < iconHelp.size(); i++) {
                sender.sendMessage(tools.replace(iconHelp.get(i)));
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED+"/bmm help");
        return true;
    }



}
