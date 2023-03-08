// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import io.github.ph1lou.werewolfapi.events.FinalDeathEvent;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import org.bukkit.Bukkit;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;

public class chasseur extends RolesVillage
{
    final Player player;
    
    public chasseur(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
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
    public void onPlayerDeath(final FinalDeathEvent event) {
        final UUID killerUUID = this.game.getPlayersWW().get(this.getPlayerUUID()).getLastKiller();
        final Player killer = Bukkit.getPlayer(killerUUID);
        killer.setHealth(killer.getMaxHealth() / 2.0);
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("[§6LG-UHC§f] §3Le Chasseur a tir\u00e9 sa fleche sur §4" + killer.getName() + "§3. Il perd donc 5 coeurs !");
        Bukkit.broadcastMessage(" ");
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.chasseur.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.chasseur.display";
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
