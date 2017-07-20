package com.chandlerpl.framework.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.chandlerpl.framework.MainClass;
import com.chandlerpl.framework.config.ConfigManager;

public class ReloadCommand extends PluginCommand {

	public ReloadCommand() {
		super("reload", "/plugin reload", "Reloads the plugin.", new String[] { "reload", "r" });
	}

	public static void execute(Player sender, List<String> args) {
			ConfigManager.reloadPlugin();
	}
}
