package org.dragon.commands;

import io.github.ph1lou.werewolfapi.rolesattributs.Roles;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

public class CommandEspritFrappeur implements Commands
{
    GetWereWolfAPI main;
    
    public CommandEspritFrappeur(final GetWereWolfAPI ww) {
        this.main = ww;
    }
    
    @SuppressWarnings("unlikely-arg-type")
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
        if (!plg.getRole().isDisplay("werewolf.role.ef.display")) {
            player.sendMessage(game.translate("werewolf.check.role", new Object[] { game.translate("werewolf.role.ef.display", new Object[0]) }));
            return;
        }
        final Roles EF = plg.getRole();
        if (args.length != 1) {
            player.sendMessage(game.translate("werewolf.check.player_input", new Object[0]));
            return;
        }
        if (!plg.isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.death", new Object[0]));
            return;
        }
        if (!((Power)EF).hasPower()) {
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
        if (((AffectedPlayers)EF).getAffectedPlayers().contains(argUUID)) {
            player.sendMessage(game.translate("werewolf.check.already_get_power", new Object[0]));
            return;
        }
        final ProtectionEvent protectionEvent = new ProtectionEvent(uuid, argUUID);
        Bukkit.getPluginManager().callEvent((Event)protectionEvent);
        if (protectionEvent.isCancelled()) {
            player.sendMessage(game.translate("werewolf.check.cancel", new Object[0]));
            return;
        }
        ((AffectedPlayers)EF).clearAffectedPlayer();
        ((Power)EF).setPower(Boolean.valueOf(false));
        ((AffectedPlayers)EF).addAffectedPlayer(argUUID);
        final Player playerProtected = Bukkit.getPlayer(args[0]);
        if (playerProtected == null) {
            return;
        }
        playerProtected.removePotionEffect(PotionEffectType.SLOW);
        playerProtected.removePotionEffect(PotionEffectType.WEAKNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, game.getConfig().getTimerValues().get("werewolf.menu.timers.day_duration") * 20 * 2, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, game.getConfig().getTimerValues().get("werewolf.menu.timers.day_duration") * 20 * 2, 0, false, false));
        game.getPlayersWW().get(argUUID).setSalvation(Boolean.valueOf(true));
        playerProtected.sendMessage(game.translate("werewolf.role.ef.get_ef", new Object[0]));
        player.sendMessage(game.translate("werewolf.role.ef.ef_perform", new Object[] { String.valueOf(args[0]) + playerProtected.getName() + "!" }));
    }
}
