// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;

public class plongeur extends RolesVillage
{
    final Player player;
    
    public plongeur(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.player = Bukkit.getPlayer(this.getPlayerUUID());
    }
    
    @EventHandler
    public void onNight(final NightEvent event) {
    }
    
    @EventHandler
    public void onDay(final DayEvent event) {
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.plongeur.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.plongeur.display";
    }
    
    public boolean isDisplay(final String s) {
        return s.equals(this.getDisplay());
    }
    
    public void stolen(final UUID uuid) {
    }
    
    public void recoverPotionEffect(final Player player) {
        super.recoverPotionEffect(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 4, false, false));
    }
}
