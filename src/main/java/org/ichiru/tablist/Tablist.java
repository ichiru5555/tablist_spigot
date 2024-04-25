package org.ichiru.tablist;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.Objects;

public final class Tablist extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                updateTabListForAllPlayers();
            }
        }.runTaskTimerAsynchronously(this, 0L, 40L);
    }

    private void updateTabListForAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerTabList(player);
        }
    }

    private void updatePlayerTabList(Player player) {
        String format = getConfig().getString("tablist-format", "&b%player_name% [%world%] %ping%");
        String footerText = getConfig().getString("tablist-footer");
        footerText = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(footerText));
        String format_footer = PlaceholderAPI.setPlaceholders(player, footerText);

        String playerListName = PlaceholderAPI.setPlaceholders(player, format);

        playerListName = ChatColor.translateAlternateColorCodes('&', playerListName);

        player.setPlayerListName(playerListName);
        player.setPlayerListFooter(format_footer);
    }
}
