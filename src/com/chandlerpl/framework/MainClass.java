package com.chandlerpl.framework;

import org.bukkit.plugin.java.JavaPlugin;
import com.chandlerpl.framework.commands.Commands;
import com.chandlerpl.framework.config.Config;
import com.chandlerpl.framework.config.ConfigManager;

public class MainClass extends JavaPlugin {
    public static MainClass instance;
    public static Config config = ConfigManager.defaultConfig;
	
    public void onEnable() {
    	instance = this;
        new ConfigManager();
		config = ConfigManager.defaultConfig;

		new Commands(instance);
		getServer().getPluginManager().registerEvents(new Commands(instance), instance);
			
        
    }

    public void onDisable() {
        
    }
}