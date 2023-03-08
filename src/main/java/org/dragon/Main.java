package org.dragon;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragon.commands.CommandEspritFrappeur;
import org.dragon.commands.CommandMageGentil;
import org.dragon.commands.CommandMageMechant;
import org.dragon.commands.CommandOracle;
import org.dragon.commands.CommandSoignant;
import org.dragon.role.Esprit_Frappeur;
import org.dragon.role.Mage;
import org.dragon.role.Oracle;
import org.dragon.role.Templier;
import org.dragon.role.barbare;
import org.dragon.role.berserker;
import org.dragon.role.chap_rouge;
import org.dragon.role.chasseur;
import org.dragon.role.chien_loup;
import org.dragon.role.gardien;
import org.dragon.role.gardien_obscure;
import org.dragon.role.maitre_archer;
import org.dragon.role.maitre_demon;
import org.dragon.role.plongeur;
import org.dragon.role.soignant;
import org.dragon.role.survivant;
import org.dragon.role.villageois_villageois;

import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.RoleRegister;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.Category;

public class Main extends JavaPlugin
{
    public static Main instance;
    public GetWereWolfAPI ww;
    public Class<GetWereWolfAPI> gwwa;
    public Class<WereWolfAPI> wwa;
    public Class<UUID> uuid;
    
    public Main() {
        this.gwwa = GetWereWolfAPI.class;
        this.wwa = WereWolfAPI.class;
        this.uuid = UUID.class;
    }
    
    public static Main getInstance() {
        return Main.instance;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void onEnable() {
        Main.instance = this;
        (this.ww = (GetWereWolfAPI)Bukkit.getPluginManager().getPlugin("WereWolfPlugin")).loadTranslation((Plugin)this, "fr");
        this.ww.getAddonsList().add(this);
        try {
            final RoleRegister Survivant = new RoleRegister((Plugin)this, this.ww, "werewolf.role.survivant.display").registerRole((Class)survivant.class);
            Survivant.setLore((List)Arrays.asList("§6Survivant", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Mage = new RoleRegister((Plugin)this, this.ww, "werewolf.role.mage.display").registerRole((Class)Mage.class);
            Mage.setLore((List)Arrays.asList("§6Mage", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.NEUTRAL).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Soignant = new RoleRegister((Plugin)this, this.ww, "werewolf.role.soignant.display").registerRole((Class)soignant.class);
            Soignant.setLore((List)Arrays.asList("§6Soignant", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Templier = new RoleRegister((Plugin)this, this.ww, "werewolf.role.templier.display").registerRole((Class)Templier.class);
            Templier.setLore((List)Arrays.asList("§6Templier", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).create();
            
            final RoleRegister Oracle = new RoleRegister((Plugin)this, this.ww, "werewolf.role.oracle.display").registerRole((Class)Oracle.class);
            Oracle.setLore((List)Arrays.asList("§6Oracle", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).create();
            
            
            this.ww.getListCommands().put("demoniaque", new CommandMageMechant(this.ww));
            this.ww.getListCommands().put("bienveillant", new CommandMageGentil(this.ww));
            this.ww.getListCommands().put("heal", new CommandSoignant(this.ww));
            this.ww.getListCommands().put("visionner", new CommandOracle(this.ww));
            
            
            final RoleRegister Maitre_Archer = new RoleRegister((Plugin)this, this.ww, "werewolf.role.maitre_archer.display").registerRole((Class)maitre_archer.class);
            Maitre_Archer.setLore((List)Arrays.asList("§6Maitre Archer", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Chien_Loup = new RoleRegister((Plugin)this, this.ww, "werewolf.role.chien_loup.display").registerRole((Class)chien_loup.class);
            Chien_Loup.setLore((List)Arrays.asList("§6Chien Loup", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.WEREWOLF).create();
            
            final RoleRegister Maitre_Demon = new RoleRegister((Plugin)this, this.ww, "werewolf.role.maitre_demon.display").registerRole((Class)maitre_demon.class);
            Maitre_Demon.setLore((List)Arrays.asList("§6Maitre Demon", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.NEUTRAL).create();
            
            final RoleRegister Gardien = new RoleRegister((Plugin)this, this.ww, "werewolf.role.gardien.display").registerRole((Class)gardien.class);
            Gardien.setLore((List)Arrays.asList("§6Gardien", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Berserker = new RoleRegister((Plugin)this, this.ww, "werewolf.role.berserker.display").registerRole((Class)berserker.class);
            Berserker.setLore((List)Arrays.asList("§6Berserker", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Barbare = new RoleRegister((Plugin)this, this.ww, "werewolf.role.barbare.display").registerRole((Class)barbare.class);
            Barbare.setLore((List)Arrays.asList("§6Barbare", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.WEREWOLF).create();
            
            final RoleRegister Gardien_Obscure = new RoleRegister((Plugin)this, this.ww, "werewolf.role.gardien_obscure.display").registerRole((Class)gardien_obscure.class);
            Gardien_Obscure.setLore((List)Arrays.asList("§6Gardien Obscure", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.NEUTRAL).create();
            
            final RoleRegister Plongeur = new RoleRegister((Plugin)this, this.ww, "werewolf.role.plongeur.display").registerRole((Class)plongeur.class);
            Plongeur.setLore((List)Arrays.asList("§6Plongeur", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Chasseur = new RoleRegister((Plugin)this, this.ww, "werewolf.role.chasseur.display").registerRole((Class)chasseur.class);
            Chasseur.setLore((List)Arrays.asList("§6Chasseur", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Chaperon_Rouge = new RoleRegister((Plugin)this, this.ww, "werewolf.role.chap_rouge.display").registerRole((Class)chap_rouge.class);
            Chaperon_Rouge.setLore((List)Arrays.asList("§6Chaperon Rouge", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister Villageois_Villageois = new RoleRegister((Plugin)this, this.ww, "werewolf.role.villageois_villageois.display").registerRole((Class)villageois_villageois.class);
            Villageois_Villageois.setLore((List)Arrays.asList("§6Villageois Villageois", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();
            
            final RoleRegister EF = new RoleRegister((Plugin)this, this.ww, "werewolf.role.ef.display").registerRole((Class)Esprit_Frappeur.class);
            EF.setLore((List)Arrays.asList("§6Esprit", "§fFait par §6DragonNium")).addCategory(Category.ADDONS).create();
            
            this.ww.getListCommands().put("curse", new CommandEspritFrappeur(this.ww));
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
