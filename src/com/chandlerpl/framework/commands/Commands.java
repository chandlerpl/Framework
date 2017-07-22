package com.chandlerpl.framework.commands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.chandlerpl.framework.Main;

import java.util.Arrays;
import java.util.List;

public class Commands implements Listener {
    public Commands(Main plugin) {
        new HoleCommand();
    }

    String[] holealiases = new String[] { "/hole"};

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final String cmd = event.getMessage().toLowerCase();
        final String[] args = cmd.split("\\s+");
        List<String> sendingArgs = null;
        Player sender = event.getPlayer();

        sendingArgs = Arrays.asList(args).subList(1, args.length);
        
        if (Arrays.asList(holealiases).contains(args[0].toLowerCase()))
        {
            HoleCommand.execute(sender, sendingArgs);
            event.setCancelled(true);
        }
    }
}