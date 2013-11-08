package com.relicum.scb.listeners;

import com.relicum.scb.SCB;
import com.relicum.scb.SmashPlayer;
import com.relicum.scb.events.PlayerJoinLobbyEvent;
import com.relicum.scb.objects.inventory.ClearInventory;
import com.relicum.scb.objects.signs.utils.Col;
import com.relicum.scb.utils.playerStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

/**
 * SuperSkyBros First Created 09/10/13
 *
 * @author Relicum
 * @version 0.1
 */
public class onBlockClick implements Listener {

    private SCB plugin;

    private List<String> blacklist;


    public onBlockClick(SCB p) {
        this.plugin = p;
        this.blacklist = plugin.getBlackList();
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onClick(PlayerInteractEvent e) {

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)

            return;

        if (this.blacklist.contains(e.getPlayer().getWorld().getName()))
            return;

        Block clicked = e.getClickedBlock();


        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && (clicked.getType() == Material.SIGN || clicked.getType() == Material.SIGN_POST || clicked.getType() == Material.WALL_SIGN)) {


            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) clicked.getState();

            String[] lines = sign.getLines();


            if ((SCB.perms.has(e.getPlayer(), "ssb.player.uselobbysign") || e.getPlayer().isOp()) && (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[JOIN LOBBY]"))) {

                if (SCB.getInstance().LBS.isInLobby(e.getPlayer())) {
                    e.getPlayer().sendMessage(SCB.getMessageManager().getErrorMessage("listeners.playerJoin.alreadyInLobby"));
                    return;
                }

                SmashPlayer splayer = SmashPlayer.wrap(e.getPlayer());

                splayer.pStatus = playerStatus.UNKNOWN;
                PlayerJoinLobbyEvent event = new PlayerJoinLobbyEvent(splayer, "SIGN", SCB.getInstance().getConfig().getBoolean("dedicatedSSB"));
                Bukkit.getServer().getPluginManager().callEvent(event);


                return;
            }

            if ((SCB.perms.has(e.getPlayer(), "ssb.player.uselobbysign") || e.getPlayer().isOp()) && (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[LEAVE]"))) {

                e.getPlayer().performCommand("ssb leave");

            }

            if ((SCB.perms.has(e.getPlayer(), "ssb.player.uselobbysign") || e.getPlayer().isOp()) && (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[RETURN]"))) {
                ClearInventory.applyLobbyInv(e.getPlayer());
                e.getPlayer().sendMessage(SCB.getMessageManager().getMessage("listeners.onblockclick.returnToLobby"));
                SCB.getInstance().LBS.teleportToLobby(e.getPlayer(), SCB.getInstance().LBS.getLobbyRegion().getWorld().getSpawnLocation());

            }

            if ((SCB.perms.has(e.getPlayer(), "ssb.player.uselobbysign") || e.getPlayer().isOp()) && (ChatColor.stripColor(lines[0]).startsWith("[Arena"))) {
                if (ChatColor.stripColor(lines[3]).equalsIgnoreCase("waiting")) {
                    e.getPlayer().sendMessage("Sign Starts with [Arena and status is waiting");
                    return;
                }
                e.getPlayer().sendMessage("Sorry sign status is not correct");
                return;


            }
        }

    }


}
