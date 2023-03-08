// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import io.github.ph1lou.werewolfapi.events.EndPlayerMessageEvent;
import org.bukkit.entity.Player;
import io.github.ph1lou.werewolfapi.enumlg.Camp;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Bukkit;
import io.github.ph1lou.werewolfapi.events.FinalDeathEvent;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import org.bukkit.event.EventHandler;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import java.util.UUID;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.rolesattributs.Power;
import io.github.ph1lou.werewolfapi.rolesattributs.Transformed;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesNeutral;

public class Mage extends RolesNeutral implements Transformed, Power
{
    private MageForm choice;
    boolean transformed;
    private boolean power;
    private boolean neutre;
    
    public boolean isNeutre() {
        return this.neutre;
    }
    
    public void setNeutre(final boolean neutre) {
        this.neutre = neutre;
    }
    
    public Mage(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.choice = MageForm.MAGE;
        this.transformed = false;
        this.neutre = true;
        this.power = true;
    }
    
    public boolean getTransformed() {
        return this.transformed;
    }
    
    public void setTransformed(final boolean transformed) {
        this.transformed = transformed;
    }
    
    public String getDescription() {
        if (this.choice.equals(MageForm.MAGE_Bienveillant)) {
            return this.game.translate("werewolf.role.mage_gentil.description", new Object[0]);
        }
        if (this.choice.equals(MageForm.MAGE_Demoniaque)) {
            return this.game.translate("werewolf.role.mage_mechant.description", new Object[0]);
        }
        return this.game.translate("werewolf.role.mage.description", new Object[0]);
    }
    
    @EventHandler
    public void onNight(final NightEvent event) {
    }
    
    @EventHandler
    public void onDay(final DayEvent event) {
    }
    
    @EventHandler
    public void onFinalDeathEvent(final FinalDeathEvent event) {
        final UUID MortUUID = event.getUuid();
        final UUID killerUUID = this.game.getPlayersWW().get(event.getUuid()).getLastKiller();
        final Player killer = Bukkit.getPlayer(killerUUID);
        if (!killerUUID.equals(this.getPlayerUUID())) {
            return;
        }
        if (Bukkit.getPlayer(this.getPlayerUUID()) != null) {
            killer.removePotionEffect(PotionEffectType.ABSORPTION);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1200, 0, false, false));
            if (this.game.getPlayersWW().containsKey(MortUUID) && this.game.getPlayersWW().get(MortUUID).getRole().isCamp(Camp.WEREWOLF)) {
                killer.setMaxHealth(killer.getMaxHealth() + 1.0);
            }
            if (this.game.getPlayersWW().containsKey(MortUUID) && this.game.getPlayersWW().get(MortUUID).getRole().isCamp(Camp.VILLAGER)) {
                if (this.choice == MageForm.MAGE_Bienveillant) {
                    killer.setMaxHealth(killer.getMaxHealth() - 1.0);
                }
                if (this.choice == MageForm.MAGE_Demoniaque) {
                    killer.setMaxHealth(killer.getMaxHealth() + 1.0);
                }
            }
            if (this.game.getPlayersWW().containsKey(MortUUID) && this.game.getPlayersWW().get(MortUUID).getRole().isCamp(Camp.NEUTRAL)) {
                killer.setMaxHealth(killer.getMaxHealth() + 2.0);
            }
        }
    }
    
    public void stolen(final UUID uuid) {
        if (Bukkit.getPlayer(this.getPlayerUUID()) == null) {
            return;
        }
        final Player player = Bukkit.getPlayer(this.getPlayerUUID());
        if (this.choice == MageForm.MAGE_Demoniaque) {
            player.sendMessage(this.game.translate("werewolf.role.mage_mechant.description", new Object[0]));
        }
        if (this.choice == MageForm.MAGE_Bienveillant) {
            player.sendMessage(this.game.translate("werewolf.role.mage_gentil.description", new Object[0]));
        }
    }
    
    public void recoverPotionEffect(final Player player) {
        super.recoverPotionEffect(player);
        if (this.choice == MageForm.MAGE_Bienveillant) {
            player.setMaxHealth(18.0);
            player.setHealth(18.0);
        }
        if (this.choice == MageForm.MAGE_Demoniaque) {
            player.setHealth(26.0);
            player.setMaxHealth(26.0);
        }
    }
    
    @EventHandler
    public void onEndPlayerMessage(final EndPlayerMessageEvent event) {
        if (!event.getPlayerUUID().equals(this.getPlayerUUID())) {
            return;
        }
        final StringBuilder sb = event.getEndMessage();
        if (this.isChoice(MageForm.MAGE_Bienveillant)) {
            sb.append(this.game.translate("werewolf.role.mage_gentil.end", new Object[0]));
        }
        if (this.isChoice(MageForm.MAGE_Demoniaque)) {
            sb.append(this.game.translate("werewolf.role.mage_mechant.end", new Object[0]));
        }
    }
    
    public String getDisplay() {
        return "werewolf.role.mage.display";
    }
    
    public boolean isChoice(final MageForm MAGE) {
        return MAGE == this.choice;
    }
    
    public MageForm getChoice() {
        return this.choice;
    }
    
    public void setChoice(final MageForm choice) {
        this.choice = choice;
    }
    
    public void setPower(final Boolean power) {
        this.power = power;
    }
    
    public Boolean hasPower() {
        return this.power;
    }
    
    public boolean isNeutral() {
        return this.neutre;
    }
    
    public enum MageForm
    {
        MAGE("MAGE", 0), 
        MAGE_Bienveillant("MAGE_Bienveillant", 1), 
        MAGE_Demoniaque("MAGE_Demoniaque", 2);
        
        private MageForm(final String name, final int ordinal) {
        }
    }
}
