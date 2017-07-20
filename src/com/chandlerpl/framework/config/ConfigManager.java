package com.chandlerpl.framework.config;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.chandlerpl.framework.MainClass;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {

	public static Config defaultConfig;

	public ConfigManager() {
		defaultConfig = new Config(new File("config.yml"));
		configCheck();
	}

	public static void configCheck() {
		FileConfiguration config;
		config = defaultConfig.get();
		config.addDefault("debug", false);
		
		defaultConfig.save();
	}
	
	public static void reloadPlugin() {
		System.out.println(ChatColor.RED + "Reloading the MainClass plugin");
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.isOp()) {
				player.sendMessage(ChatColor.RED + "Reloading the MainClass plugin");
			}
		}
		ConfigManager.defaultConfig.reload();
		System.out.println(ChatColor.GREEN + "Reload complete");
		if(MainClass.config.get().getBoolean("debug")) {
			System.out.println(ChatColor.GOLD + "The MainClass is in debug mode!");
		}
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.isOp()) {
				player.sendMessage(ChatColor.GREEN + "MainClass reload complete, version: " + MainClass.instance.getDescription().getVersion());
				if(MainClass.config.get().getBoolean("debug")) {
					player.sendMessage(ChatColor.GOLD + "The MainClass is in debug mode!");
				}
			}
		}
	}
}
