/*
 * jaoLicense
 *
 * Copyright (c) 2021 jao Minecraft Server
 *
 * The following license applies to this project: jaoLicense
 *
 * Japanese: https://github.com/jaoafa/jao-Minecraft-Server/blob/master/jaoLICENSE.md
 * English: https://github.com/jaoafa/jao-Minecraft-Server/blob/master/jaoLICENSE-en.md
 */

package com.jaoafa.mymaid4.event;

import com.jaoafa.mymaid4.lib.MyMaidLibrary;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class Event_FarmNoBreak extends MyMaidLibrary implements Listener {
    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        EntityType type = event.getEntityType();
        if (type == EntityType.PLAYER) {
            return;
        } else if (type == EntityType.VILLAGER) {
            return;
        } else if (type == EntityType.FALLING_BLOCK) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onFrom(EntityChangeBlockEvent event) {
        if (event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
