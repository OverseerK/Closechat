package com.overseer.closechat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Tabcompletion implements TabCompleter {
        public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String arg, String[] args) {
            if (cmd.getName().equalsIgnoreCase("closechat") && args.length == 1) {
                List<String> list = new ArrayList<>();
                list.add("reload");
                list.add("worlds");
                return list;
            } else {
                return null;
            }
        }
}
