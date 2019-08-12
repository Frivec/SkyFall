package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Archer extends Kit {
	
	public Archer() {
		super("Archer", 1, 5000, new ItemCreator(Material.ARROW, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 1).setDisplayName(fileManager.getLine("kits.archer.name")).setLores(fileManager.getStringList("kits.archer.description")).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.BOW, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 1).build());
		registerItem(new ItemCreator(Material.ARROW, 12).build());
	}

}
