// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.commands;

import io.github.ph1lou.werewolfapi.rolesattributs.Roles;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.Camp;
import io.github.ph1lou.werewolfapi.rolesattributs.Display;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.Event;
import io.github.ph1lou.werewolfapi.events.SeerEvent;
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

public class CommandOracle implements Commands
{
    GetWereWolfAPI main;
    
    public CommandOracle(final GetWereWolfAPI ww) {
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
        if (!plg.getRole().isDisplay("werewolf.role.oracle.display")) {
            player.sendMessage(game.translate("werewolf.check.role", new Object[] { game.translate("werewolf.role.oracle.display", new Object[0]) }));
            return;
        }
        final Roles oracle = plg.getRole();
        if (args.length != 1) {
            player.sendMessage(game.translate("werewolf.check.player_input", new Object[0]));
            return;
        }
        if (!plg.isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.death", new Object[0]));
            return;
        }
        if (!((Power)oracle).hasPower()) {
            player.sendMessage(game.translate("werewolf.check.power", new Object[0]));
            return;
        }
        final Player playerCible = Bukkit.getPlayer(args[0]);
        if (playerCible == null) {
            player.sendMessage(game.translate("werewolf.check.offline_player", new Object[0]));
            return;
        }
        final UUID cibleUUID = playerCible.getUniqueId();
        if (!game.getPlayersWW().containsKey(cibleUUID) || !game.getPlayersWW().get(cibleUUID).isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.player_not_found", new Object[0]));
            return;
        }
        if (((AffectedPlayers)oracle).getAffectedPlayers().contains(cibleUUID)) {
            player.sendMessage(game.translate("werewolf.check.already_get_power", new Object[0]));
            return;
        }
        final SeerEvent seerEvent = new SeerEvent(uuid, cibleUUID);
        Bukkit.getPluginManager().callEvent((Event)seerEvent);
        if (seerEvent.isCancelled()) {
            player.sendMessage(game.translate("werewolf.check.cancel", new Object[0]));
            return;
        }
        ((AffectedPlayers)oracle).clearAffectedPlayer();
        ((Power)oracle).setPower(Boolean.valueOf(false));
        ((AffectedPlayers)oracle).addAffectedPlayer(cibleUUID);
        
        
        final PlayerWW plg2 = game.getPlayersWW().get(cibleUUID);
        final Roles role1 = plg2.getRole();
        if ((role1 instanceof Display && ((Display)role1).isDisplayCamp(Camp.VILLAGER)) || role1.isCamp(Camp.VILLAGER)) {
            player.sendMessage(" ");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
            player.sendMessage(String.valueOf(game.translate("werewolf.role.oracle.ifbasique", new Object[0])) + "§b" + playerCible.getName() + game.translate("werewolf.role.oracle.ifvivi", new Object[0]));
            player.sendMessage(" ");
        }
        else if ((role1 instanceof Display && ((Display)role1).isDisplayCamp(Camp.WEREWOLF)) || (!(role1 instanceof Display) && role1.isCamp(Camp.WEREWOLF))) {
            player.sendMessage(" ");
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, -1, false, false));
            player.sendMessage(String.valueOf(game.translate("werewolf.role.oracle.ifbasique", new Object[0])) + "§4" + playerCible.getName() + game.translate("werewolf.role.oracle.iflg", new Object[0]));
            player.sendMessage(" ");
        }
        else if ((role1 instanceof Display && ((Display)role1).isDisplayCamp(Camp.NEUTRAL)) || (!(role1 instanceof Display) && role1.isCamp(Camp.NEUTRAL))) {
            player.sendMessage(" ");
            player.sendMessage(String.valueOf(game.translate("werewolf.role.oracle.ifbasique", new Object[0])) + "§5" + playerCible.getName() + game.translate("werewolf.role.oracle.ifneutre", new Object[0]));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, -1, false, false));
            player.sendMessage(" ");
        }
    }
}
