package com.chandlerpl.framework;

import org.bukkit.plugin.java.JavaPlugin;

import com.chandlerpl.framework.commands.Commands;

public class Main extends JavaPlugin {
    public static Main instance;

    public void onEnable() {
        instance = this;
        new Commands(instance);
        getServer().getPluginManager().registerEvents(new Commands(instance), instance);
    }

    public void onDisable() {
    }
}