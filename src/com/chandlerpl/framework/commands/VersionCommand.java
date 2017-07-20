package com.chandlerpl.framework.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.chandlerpl.framework.MainClass;

public class VersionCommand extends PluginCommand {

	public VersionCommand() {
		super("version", "/plugin version", "Displays the version of the plugin.", new String[] { "version", "v" });
	}

	public static void execute(Player sender, List<String> args) {
		sender.sendMessage(ChatColor.DARK_GREEN + "=====Plugin=====");
		sender.sendMessage(ChatColor.DARK_GREEN + "Version: " + ChatColor.GREEN + MainClass.instance.getDescription().getVersion());
		List<String> authors = MainClass.instance.getDescription().getAuthors();
		sender.sendMessage(ChatColor.GREEN + "Head Developer: " + authors.get(0));
		authors = authors.subList(1, authors.size());
		if(authors.size() == 0) {
			sender.sendMessage(ChatColor.GREEN + "Developers: " + authors.toString());
		}
	}

}
