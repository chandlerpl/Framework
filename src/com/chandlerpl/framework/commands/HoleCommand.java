package com.chandlerpl.framework.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class HoleCommand extends FrameworkCommand {
    public HoleCommand() {
        super("hole", "/hole <player>", "Drops player into void.", new String[] { "hole"});
    }

    public static void execute(Player sender, List<String> args) {
        sender.sendMessage(ChatColor.DARK_GREEN + "Testing");
    }
}