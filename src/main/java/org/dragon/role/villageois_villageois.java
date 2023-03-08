// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;

public class villageois_villageois extends RolesVillage
{
    final Player player;
    
    public villageois_villageois(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
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
        return this.game.translate("werewolf.role.villageois_villageois.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.villageois_villageois.display";
    }
    
    public boolean isDisplay(final String s) {
        return s.equals(this.getDisplay());
    }
    
    public void stolen(final UUID uuid) {
    }
    
    public void recoverPotionEffect(final Player player) {
        super.recoverPotionEffect(player);
        Bukkit.broadcastMessage("§e§lLe Simple Villageois est §6§l" + player.getName() + "§e§l !");
    }
}
