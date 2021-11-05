package com.overseer.closechat;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public final class Closechat extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[CloseChat] Enabled.");
        Objects.requireNonNull(getCommand("closechat")).setTabCompleter(new Tabcompletion());
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[CloseChat] Disabled.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("closechat")) {
            if (args.length == 0) {
                sender.sendMessage("[CloseChat] Closechat commands: /closechat reload, /closechat worlds");
            } else if (args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                sender.sendMessage("[CloseChat] Configuration file reloaded.");
            } else if (args[0].equalsIgnoreCase("worlds")) {
                sender.sendMessage("적용된 월드 목록:");
                ArrayList<String> WorldNameList = (ArrayList<String>) config.getStringList("월드 이름");
                if (WorldNameList.size() > 0) {
                    for (String WorldName : WorldNameList) {
                        World w = Bukkit.getWorld(WorldName);
                        if (w == null) {
                            System.err.println("[CloseChat] 월드가 유효하지 않습니다: " + WorldName);
                        } else {
                            sender.sendMessage("- " + WorldName);
                        }
                    }
                }
            } else {
                sender.sendMessage("[CloseChat] Closechat commands: /closechat reload, /closechat worlds");
            }
        }
        return false;
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