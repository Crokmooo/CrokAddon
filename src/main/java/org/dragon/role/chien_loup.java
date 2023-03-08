// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesWereWolf;

public class chien_loup extends RolesWereWolf
{
    final Player player;
    
    public chien_loup(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.player = Bukkit.getPlayer(this.getPlayerUUID());
    }
    
    @EventHandler
    public void onNight(final NightEvent event) {
        this.player.removePotionEffect(PotionEffectType.SPEED);
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, -1, false, false));
    }
    
    @EventHandler
    public void onDay(final DayEvent event) {
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, -1, false, false));
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.chien_loup.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.chien_loup.display";
    }
    
    public boolean isDisplay(final String s) {
        return s.equals(this.getDisplay());
    }
    
    public void stolen(final UUID uuid) {
    }
}
