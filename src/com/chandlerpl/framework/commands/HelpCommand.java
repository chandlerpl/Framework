package com.chandlerpl.framework.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends PluginCommand {
	public HelpCommand() {
		super("help", "/plugin help", "This command provides information on how to use commands in the plugin.", new String[] { "help", "h" });
	}

	
	public static void execute(Player sender, List<String> args) {
		if (args == null || args.size() == 0) {
			sender.sendMessage(ChatColor.DARK_GREEN + "=====Tplugin Help=====");
			
			for(String string : PluginCommand.instances.keySet()) {
				sender.sendMessage(ChatColor.GREEN + "/plugin " + string);
			}
			return;
		}

		String arg = args.get(0);
		
		if (PluginCommand.instances.keySet().contains(arg.toLowerCase())) {
			PluginCommand.instances.get(arg);
			PluginCommand.help(sender, true);
		}
	}
}
