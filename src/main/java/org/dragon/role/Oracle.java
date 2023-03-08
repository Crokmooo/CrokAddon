package org.dragon.role;

import org.bukkit.potion.PotionEffect;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import java.util.ArrayList;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import java.util.UUID;
import java.util.List;
import io.github.ph1lou.werewolfapi.rolesattributs.AffectedPlayers;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesWithLimitedSelectionDuration;

public class Oracle extends RolesWithLimitedSelectionDuration implements AffectedPlayers
{
    private final List<UUID> affectedPlayer;
    
    public Oracle(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.affectedPlayer = new ArrayList<UUID>();
        this.setPower(Boolean.valueOf(false));
    }
    
    public void addAffectedPlayer(final UUID uuid) {
        this.affectedPlayer.add(uuid);
    }
    
    public void removeAffectedPlayer(final UUID uuid) {
        this.affectedPlayer.remove(uuid);
    }
    
    public void clearAffectedPlayer() {
        this.affectedPlayer.clear();
    }
    
    public List<UUID> getAffectedPlayers() {
        return this.affectedPlayer;
    }
    
    @EventHandler
    public void onDay(final DayEvent event) {
        if (this.getPlayerUUID() == null) {
            return;
        }
        if (!this.game.getPlayersWW().get(this.getPlayerUUID()).isState(State.ALIVE)) {
            return;
        }
        final Player player = Bukkit.getPlayer(this.getPlayerUUID());
        this.setPower(Boolean.valueOf(true));
        if (player == null) {
            return;
        }
        player.sendMessage(this.game.translate("werewolf.role.oracle.if_message", new Object[0]));
    }
    
    @EventHandler
    public void OnNight(final NightEvent event) {
        if (this.getPlayerUUID() == null) {
            return;
        }
        if (!this.game.getPlayersWW().get(this.getPlayerUUID()).isState(State.ALIVE)) {
            return;
        }
        final Player player = Bukkit.getPlayer(this.getPlayerUUID());
        this.setPower(Boolean.valueOf(false));
        for (final PotionEffect type : player.getActivePotionEffects()) {
            player.removePotionEffect(type.getType());
        }
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.oracle.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.oracle.display";
    }
}
