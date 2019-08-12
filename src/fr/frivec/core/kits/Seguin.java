package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Seguin extends Kit {

	public Seguin() {
		super("Mr. Seguin", 6, 5000, new ItemCreator(Material.WOOL, 1).setDisplayName(fileManager.getLine("kits.seguin.name")).setLores(fileManager.getStringList("kits.seguin.description")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.MONSTER_EGG, 10, EntityType.SHEEP.getTypeId()).build());
		registerItem(new ItemCreator(Material.SHEARS, 1).build());
	}

}
