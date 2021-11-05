package com.overseer.closechat;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Closechat extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[CloseChat] Enabled.");
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[CloseChat] Disabled.");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        int Radius = config.getInt("범위");
        ArrayList<String> WorldNameList = (ArrayList<String>) config.getStringList("월드 이름");
        if (WorldNameList.size() > 0) {
            for (String WorldName : WorldNameList) {
                World w = Bukkit.getWorld(WorldName);
                if (w == null) {
                    System.err.println("[CloseChat] 월드가 유효하지 않습니다: " + WorldName);
                } else if (w == e.getPlayer().getWorld()) {
                    for (Player Recipient : e.getRecipients()) {
                        if (Recipient.getLocation().distance(p.getLocation()) > Radius && !Recipient.hasPermission("closechat.bypass")) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}