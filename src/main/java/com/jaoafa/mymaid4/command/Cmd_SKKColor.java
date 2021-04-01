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

package com.jaoafa.mymaid4.command;

import cloud.commandframework.Command;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.jaoafa.mymaid4.lib.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Cmd_SKKColor extends MyMaidLibrary implements CommandPremise {
    @Override
    public MyMaidCommand.Detail details() {
        return new MyMaidCommand.Detail(
            "skkcolor",
            "チャット欄に表示される四角の色を変更します。"
        );
    }

    @Override
    public MyMaidCommand.Cmd register(Command.Builder<CommandSender> builder) {
        return new MyMaidCommand.Cmd(
            builder
                .meta(CommandMeta.DESCRIPTION, "チャット欄に表示される四角の色をリセットします。")
                .senderType(Player.class)
                .handler(this::setSKKColor)
                .build(),
            builder
                .meta(CommandMeta.DESCRIPTION, "チャット欄に表示される四角の色を変更します。")
                .senderType(Player.class)
                .argument(StringArgument.<CommandSender>newBuilder("color")
                    .withSuggestionsProvider(this::suggestColors))
                .handler(this::setSKKColor)
                .build()
        );
    }

    void setSKKColor(CommandContext<CommandSender> context) {
        Player player = (Player) context.getSender();
        String strColor = context.getOrDefault("color", null);
        ChatColor color;
        try {
            color = strColor != null ? ChatColor.valueOf(strColor) : null;
        }catch (IllegalArgumentException e){
            SendMessage(player, details(), "指定された色は見つかりませんでした。");
            return;
        }

        PlayerVoteDataMono pvd = new PlayerVoteDataMono(player);
        int count = pvd.getVoteCount();

        if(count < 200){
            // 200未満の場合カスタムメッセージ変更不可
            SendMessage(player, details(), "あなたの投票数では四角色の変更ができません。monocraft.netで200投票を目指しましょう！");
            return;
        }

        pvd.setCustomColor(color);
        SendMessage(player, details(), "四角色を" + (color != null ? "設定" : "リセット") + "しました！");
    }

    private List<String> suggestColors(@NonNull CommandContext<CommandSender> context, String current) {
        return Arrays.stream(ChatColor.values())
            .map(Enum::name)
            .filter(s -> s.toLowerCase().startsWith(current.toLowerCase()))
            .collect(Collectors.toList());
    }
}
