package com.overseer.closechat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Closechat extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[CloseChat] 활성화됨.");
        Bukkit.getPluginManager().registerEvents(this, this);
        config.addDefault("범위", 32);
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[CloseChat] 비활성화됨.");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        int Radius = config.getInt("범위");
        for (Player Recipient : e.getRecipients()) {
            if (Recipient.getLocation().distance(p.getLocation()) > Radius && !Recipient.isOp()) {
                e.setCancelled(true);
            }
        }
    }
}