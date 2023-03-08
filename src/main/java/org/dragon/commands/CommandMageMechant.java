// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.commands;

import io.github.ph1lou.werewolfapi.rolesattributs.Roles;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.rolesattributs.Power;
import org.dragon.role.Mage;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.StateLG;
import io.github.ph1lou.werewolfapi.PlayerWW;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.Commands;

public class CommandMageMechant implements Commands
{
    GetWereWolfAPI main;
    WereWolfAPI game;
    
    public CommandMageMechant(final GetWereWolfAPI main) {
        this.main = main;
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
        if (!plg.getRole().isDisplay("werewolf.role.mage.display")) {
            player.sendMessage(game.translate("werewolf.check.role", new Object[] { game.translate("werewolf.role.mage.display", new Object[0]) }));
            return;
        }
        if (!plg.isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.death", new Object[0]));
            return;
        }
        final Roles Mage = plg.getRole();
        if (!((Mage)Mage).isChoice(org.dragon.role.Mage.MageForm.MAGE)) {
            player.sendMessage(game.translate("werewolf.check.power", new Object[0]));
            return;
        }
        if (!((Power)Mage).hasPower()) {
            player.sendMessage(game.translate("werewolf.check.power", new Object[0]));
            return;
        }
        ((Mage)Mage).setChoice(org.dragon.role.Mage.MageForm.MAGE_Demoniaque);
        player.setMaxHealth(26.0);
        player.setHealth(26.0);
        ((Mage)Mage).setNeutre(true);
        Mage.isNeutral();
        sender.sendMessage(game.translate("werewolf.role.mage_mechant.description", new Object[0]));
        ((Power)Mage).setPower(Boolean.valueOf(false));
    }
}
