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

import com.jaoafa.mymaid4.lib.EventPremise;
import com.jaoafa.mymaid4.lib.MyMaidData;
import com.jaoafa.mymaid4.lib.MyMaidLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.stream.Collectors;

public class Event_Looking extends MyMaidLibrary implements Listener, EventPremise {
    @Override
    public String description() {
        return "lookingコマンドに関する処理を行います。";
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().toBlockLocation() == event.getTo().toBlockLocation()) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!MyMaidData.isLookingMe(player.getUniqueId())) {
                return;
            }
            // 誰かが見ている
            Set<Player> looking = MyMaidData.getLookingMe(player.getUniqueId()).stream()
                .map(Bukkit::getPlayer)
                .collect(Collectors.toSet());
            for (Player p : looking) {
                Vector vector = p.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                p.teleport(p.getLocation().setDirection(vector));
            }
        }
    }
}
