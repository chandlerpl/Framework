package com.chandlerpl.framework.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.chandlerpl.framework.MainClass;
import com.chandlerpl.framework.config.ConfigManager;

public class Commands implements Listener {
	
	public static Set<String> invincible = new HashSet<String>();
	public static boolean debugEnabled = false;
	public static boolean isToggledForAll = false;
	
	
	public Commands(MainClass plugin) {
		debugEnabled = MainClass.config.get().getBoolean("debug");
		new VersionCommand();
		new HelpCommand();
		new ReloadCommand();
	}

	String[] helpaliases = new String[] { "help", "h" };
	String[] versionaliases = new String[] { "version", "v" };
	String[] reloadaliases = new String[] { "reload", "r" };
	
	//Miscellaneous
	public static String[] cmdaliases = new String[] { "/plugin"};

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		final String cmd = event.getMessage().toLowerCase();
		final String[] args = cmd.split("\\s+");
		List<String> sendingArgs = null;
		Player sender = event.getPlayer();
				
		if (Arrays.asList(cmdaliases).contains(args[0].toLowerCase()) && args.length < 2) 
		{			
			HelpCommand.execute(sender, null);
			event.setCancelled(true);
			return;
		}
		
		if(args.length < 2)
			return;
		else
			sendingArgs = Arrays.asList(args).subList(2, args.length);
		
		if (Arrays.asList(cmdaliases).contains(args[0].toLowerCase())) {
			if (Arrays.asList(helpaliases).contains(args[1])) {
				HelpCommand.execute(sender, sendingArgs);
				event.setCancelled(true);
			} else if (Arrays.asList(versionaliases).contains(args[1])) {
				VersionCommand.execute(sender, sendingArgs);
				event.setCancelled(true);
			} else if (Arrays.asList(reloadaliases).contains(args[1])) {
				ConfigManager.reloadPlugin();
				event.setCancelled(true);
			}
		}
	}	
}
