package com.chandlerpl.framework.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FrameworkCommand {

    private static String name;

    private static String properUse;

    private static String description;

    private final String[] aliases;

    public static Map<String, FrameworkCommand> instances = new HashMap<String, FrameworkCommand>();

    public FrameworkCommand(String name, String properUse, String description, String[] aliases) {
        FrameworkCommand.name = name;
        FrameworkCommand.properUse = properUse;
        FrameworkCommand.description = description;
        this.aliases = aliases;
        instances.put(name, this);
    }

    public static String getName() {
        return name;
    }

    public String getProperUse() {
        return properUse;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public static void help(Player sender, boolean description) {
        sender.sendMessage(ChatColor.GOLD + "Proper Usage: " + ChatColor.DARK_AQUA + properUse);
        if (description) {
            sender.sendMessage(ChatColor.YELLOW + FrameworkCommand.description);
        }
    }

    protected static boolean correctLength(Player sender, int size, int min, int max) {
        if (size < min || size > max) {
            FrameworkCommand.help(sender, false);
            return false;
        } else {
            return true;
        }
    }

    protected static boolean isPlayer(Player sender) {
        if (sender instanceof Player) {
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use that command.");
            return false;
        }
    }
}