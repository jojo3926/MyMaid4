package com.jaoafa.mymaid4.command;

import cloud.commandframework.Command;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.jaoafa.mymaid4.lib.CommandPremise;
import com.jaoafa.mymaid4.lib.MyMaidCommand;
import com.jaoafa.mymaid4.lib.MyMaidLibrary;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Cmd_Ded extends MyMaidLibrary implements CommandPremise {
    @Override
    public MyMaidCommand.Detail details() {
        return new MyMaidCommand.Detail(
            "ded",
            "最後に死亡した場所にテレポートします。"
        );
    }

    @Override
    public MyMaidCommand.Cmd register(Command.Builder<CommandSender> builder) {
        return new MyMaidCommand.Cmd(
            builder
                .meta(CommandMeta.DESCRIPTION, "最後に死亡した場所にテレポートします。")
                .senderType(Player.class)
                .handler(this::tpLastDed)
                .build()
        );
    }
    public static Map<String, Location> lastded = new HashMap<String, Location>();
    void tpLastDed(CommandContext<CommandSender> context) {
        Player player = (Player) context.getSender();
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            SendMessage(player, details(), "Dedコマンドはサバイバル・アドベンチャーモードでは利用できません。\nクリエイティブモードに切り替えてから実行してください。");
            SendMessage(player, details(), ChatColor.RED + "" + ChatColor.BOLD + "===[!]警告===\nPvP等での「/ded」コマンドの利用は原則禁止です！\n多く使用すると迷惑行為として認識される場合もあります！");
            return;
        }
        if (!lastded.containsKey(player.getName())) {
            SendMessage(player, details(), "最後に死亡した場所が見つかりませんでした。");
        }
        else {
            Location location = lastded.get(player.getName());
            player.teleport(location);
            SendMessage(player, details(), "最終死亡場所"+ChatColor.BOLD+"( X:"+location.getBlockX()+" Y:"+location.getBlockY()+" Z:"+location.getBlockZ()+" )"+ChatColor.RESET+"にテレポートしました。");
            SendMessage(player, details(), ChatColor.RED + "" + ChatColor.BOLD + "===[!]警告===\nPvP等での「/ded」コマンドの利用は原則禁止です！\n多く使用すると迷惑行為として認識される場合もあります！");
        }
    }
}
