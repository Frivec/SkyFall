package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Pionnier extends Kit {
	
	public Pionnier() {
		super("Pionnier", 5, 5000, new ItemCreator(Material.IRON_PICKAXE, 1).setDisplayName(fileManager.getLine("kits.pionnier.name")).setLores(fileManager.getStringList("kits.pionnier.description")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.IRON_PICKAXE, 1).build());
		registerItem(new ItemCreator(Material.STONE, 16).build());
		registerItem(new ItemCreator(Material.LEATHER_HELMET, 1).build());
	}

}
