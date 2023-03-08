// 
// Decompiled by Procyon v0.5.36
// 

package org.dragon.role;

import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import io.github.ph1lou.werewolfapi.rolesattributs.Roles;
import io.github.ph1lou.werewolfapi.PlayerWW;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import java.util.UUID;
import java.util.List;
import io.github.ph1lou.werewolfapi.rolesattributs.AffectedPlayers;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesWithLimitedSelectionDuration;

public class Esprit_Frappeur extends RolesWithLimitedSelectionDuration implements AffectedPlayers
{
    private final List<UUID> affectedPlayer;
    private boolean neutre;
    
    public Esprit_Frappeur(final GetWereWolfAPI main, final WereWolfAPI game, final UUID uuid) {
        super(main, game, uuid);
        this.affectedPlayer = new ArrayList<UUID>();
        this.setPower(Boolean.valueOf(false));
    }
    
    public boolean isNeutre() {
        return this.neutre;
    }
    
    public void setNeutre(final boolean neutre) {
        this.neutre = neutre;
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
    
    public void recoverPotionEffect(final Player player) {
        final UUID uuid = player.getUniqueId();
        final PlayerWW plg = this.game.getPlayersWW().get(uuid);
        final Roles Esprit_Frappeur = plg.getRole();
        ((Esprit_Frappeur)Esprit_Frappeur).setNeutre(false);
    }
    
    @EventHandler
    public void onNight(final NightEvent event) {
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
        player.sendMessage(this.game.translate("werewolf.role.ef.ef_message", new Object[0]));
    }
    
    public String getDescription() {
        return this.game.translate("werewolf.role.esprit_frappeur.description", new Object[0]);
    }
    
    public String getDisplay() {
        return "werewolf.role.esprit_frappeur.display";
    }
}
