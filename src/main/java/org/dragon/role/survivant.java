package org.dragon.role;

import org.bukkit.event.EventPriority;
import io.github.ph1lou.werewolfapi.enumlg.Camp;
import io.github.ph1lou.werewolfapi.events.FirstDeathEvent;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.rolesattributs.Power;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;

public class survivant extends RolesVillage implements Power
{
    final Player player;
    private boolean power;
    
    public survivant(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.player = Bukkit.getPlayer(this.getPlayerUUID());
        this.power = true;
    }
    
    @EventHandler
    public void onNight(final NightEvent event) {
    }
    
    @EventHandler
    public void onDay(final DayEvent event) {
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onFirstDeathEvent(final FirstDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getUuid().equals(this.getPlayerUUID())) {
            return;
        }
        if (!this.hasPower()) {
            return;
        }
        this.setPower(false);
        final UUID killerUUID = this.game.getPlayersWW().get(this.getPlayerUUID()).getLastKiller();
        if (Bukkit.getPlayer(this.getPlayerUUID()) != null) {
            final Player player = Bukkit.getPlayer(this.getPlayerUUID());
            if (this.game.getPlayersWW().containsKey(killerUUID) && this.game.getPlayersWW().get(killerUUID).getRole().isCamp(Camp.VILLAGER)) {
                player.setMaxHealth(18.0);
                player.setHealth(18.0);
            }
            if (this.game.getPlayersWW().containsKey(killerUUID) && this.game.getPlayersWW().get(killerUUID).getRole().isCamp(Camp.WEREWOLF)) {
                player.setMaxHealth(22.0);
                player.setHealth(22.0);
            }
            if (this.game.getPlayersWW().containsKey(killerUUID) && this.game.getPlayersWW().get(killerUUID).getRole().isCamp(Camp.NEUTRAL)) {
                player.setMaxHealth(24.0);
                player.setHealth(24.0);
            }
        }
        this.game.resurrection(this.getPlayerUUID());
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.survivant.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.survivant.display";
    }
    
    public boolean isDisplay(final String s) {
        return s.equals(this.getDisplay());
    }
    
    public void stolen(final UUID uuid) {
    }
    
    public void recoverPotionEffect(final Player player) {
        super.recoverPotionEffect(player);
    }
    
    public Boolean hasPower() {
        return this.power;
    }
    
    public void setPower(final Boolean power) {
        this.power = power;
    }
}
