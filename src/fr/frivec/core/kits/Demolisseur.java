package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Demolisseur extends Kit {
	
	public Demolisseur() {
		super("Démolisseur", 2, 5000, new ItemCreator(Material.TNT, 1).addEnchantment(Enchantment.DAMAGE_ALL, 1).setDisplayName(fileManager.getLine("kits.demolisseur.name")).setLores(fileManager.getStringList("kits.demolisseur.description")).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.TNT, 3).setDisplayName("§cTNT instantannées").build());
		registerItem(new ItemCreator(Material.SHIELD, 1).setDisplayName("§bBouclier anti-explosions").setDurability((short) 334).build());
	}
	
	//Détecter quand un joueur reçoit des dégats et s'il est en train de bloquer avec un bouclier renommé et qu'il possède le kit démolisseur.

}
