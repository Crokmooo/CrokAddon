// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.PlayerDeathEvent;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;

public class berserker extends RolesVillage
{
    final Player player;
    
    public berserker(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.player = Bukkit.getPlayer(this.getPlayerUUID());
    }
    
    @EventHandler
    public void onNight(final NightEvent event) {
    }
    
    @EventHandler
    public void onDay(final DayEvent event) {
    }
    
    @EventHandler
    private void onPlayerDeath(final PlayerDeathEvent event) {
        if (event.getEntity() == null) {
            return;
        }
        if (event.getEntity().getKiller() == null) {
            return;
        }
        final Player killer = event.getEntity().getKiller();
        if (!killer.getUniqueId().equals(this.getPlayerUUID())) {
            return;
        }
        killer.removePotionEffect(PotionEffectType.ABSORPTION);
        killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 0, false, false));
        killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 0, false, false));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, -1, false, false));
        killer.setMaxHealth(killer.getMaxHealth() + 1.0);
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.berserker.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.berserker.display";
    }
    
    public boolean isDisplay(final String s) {
        return s.equals(this.getDisplay());
    }
    
    public void stolen(final UUID uuid) {
    }
    
    public void recoverPotionEffect(final Player player) {
        super.recoverPotionEffect(player);
    }
}
