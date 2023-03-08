// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.commands;

import io.github.ph1lou.werewolfapi.rolesattributs.Roles;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import org.bukkit.event.Event;
import io.github.ph1lou.werewolfapi.events.ProtectionEvent;
import io.github.ph1lou.werewolfapi.rolesattributs.AffectedPlayers;
import org.bukkit.Bukkit;
import io.github.ph1lou.werewolfapi.rolesattributs.Power;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.StateLG;
import io.github.ph1lou.werewolfapi.PlayerWW;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.Commands;

public class CommandSoignant implements Commands
{
    GetWereWolfAPI main;
    
    public CommandSoignant(final GetWereWolfAPI ww) {
        this.main = ww;
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        final WereWolfAPI game = this.main.getWereWolfAPI();
        if (!(sender instanceof Player)) {
            sender.sendMessage(game.translate("werewolf.check.console", new Object[0]));
            return;
        }
        final Player player = (Player)sender;
        final UUID uuid = player.getUniqueId();
        if (!game.getPlayersWW().containsKey(uuid)) {
            player.sendMessage(game.translate("werewolf.check.not_in_game", new Object[0]));
            return;
        }
        final PlayerWW plg = game.getPlayersWW().get(uuid);
        if (!game.isState(StateLG.GAME)) {
            player.sendMessage(game.translate("werewolf.check.game_not_in_progress", new Object[0]));
            return;
        }
        if (!plg.getRole().isDisplay("werewolf.role.soignant.display")) {
            player.sendMessage(game.translate("werewolf.check.role", new Object[] { game.translate("werewolf.role.soignant.display", new Object[0]) }));
            return;
        }
        final Roles soignant = plg.getRole();
        if (args.length != 1) {
            player.sendMessage(game.translate("werewolf.check.player_input", new Object[0]));
            return;
        }
        if (!plg.isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.death", new Object[0]));
            return;
        }
        if (!((Power)soignant).hasPower()) {
            player.sendMessage(game.translate("werewolf.check.power", new Object[0]));
            return;
        }
        final Player playerArg = Bukkit.getPlayer(args[0]);
        if (playerArg == null) {
            player.sendMessage(game.translate("werewolf.check.offline_player", new Object[0]));
            return;
        }
        final UUID argUUID = playerArg.getUniqueId();
        if (!game.getPlayersWW().containsKey(argUUID) || !game.getPlayersWW().get(argUUID).isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.player_not_found", new Object[0]));
            return;
        }
        if (((AffectedPlayers)soignant).getAffectedPlayers().contains(argUUID)) {
            player.sendMessage(game.translate("werewolf.check.already_get_power", new Object[0]));
            return;
        }
        final ProtectionEvent protectionEvent = new ProtectionEvent(uuid, argUUID);
        Bukkit.getPluginManager().callEvent((Event)protectionEvent);
        if (protectionEvent.isCancelled()) {
            player.sendMessage(game.translate("werewolf.check.cancel", new Object[0]));
            return;
        }
        ((AffectedPlayers)soignant).clearAffectedPlayer();
        ((Power)soignant).setPower(Boolean.valueOf(false));
        ((AffectedPlayers)soignant).addAffectedPlayer(argUUID);
        final Player playerProtected = Bukkit.getPlayer(args[0]);
        if (playerProtected == null) {
            return;
        }
        playerProtected.setHealth(playerProtected.getMaxHealth());
        game.getPlayersWW().get(argUUID).setSalvation(Boolean.valueOf(true));
        playerProtected.sendMessage(game.translate("werewolf.role.soignant.get_heal", new Object[0]));
        player.sendMessage(game.translate("werewolf.role.soignant.heal_perform", new Object[] { String.valueOf(args[0]) + playerProtected.getName() + "§7!" }));
    }
}
