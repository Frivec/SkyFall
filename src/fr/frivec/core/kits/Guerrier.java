package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Guerrier extends Kit {
	
	public Guerrier() {
		super("Guerrier", 4, 5000, new ItemCreator(Material.IRON_SWORD, 1).setDisplayName(fileManager.getLine("kits.guerrier.name")).setLores(fileManager.getStringList("kits.guerrier.description")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.STONE_SWORD, 1).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
		registerItem(new ItemCreator(Material.CHAINMAIL_CHESTPLATE, 1).build());
		registerItem(new ItemCreator(Material.IRON_LEGGINGS, 1).build());
	}

}
