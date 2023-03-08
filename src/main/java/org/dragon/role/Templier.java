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
import io.github.ph1lou.werewolfapi.rolesattributs.RolesNeutral;

public class Templier extends RolesNeutral
{
    final Player player;
    
    public Templier(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
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
        killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 300, 4, false, false));
        killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, -1, false, false));
        killer.sendMessage("");
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.templier.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.templier.display";
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
