package fr.frivec.core.kits;

import org.bukkit.Material;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Enchanteur extends Kit {
	
	public Enchanteur() {
		super("Enchanteur", 3, 5000, new ItemCreator(Material.EXP_BOTTLE, 1).setDisplayName(fileManager.getLine("kits.enchanteur.name")).setLores(fileManager.getStringList("kits.enchanteur.description")).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.EXP_BOTTLE, 16).build());
		registerItem(new ItemCreator(Material.INK_SACK, 8, (short) 4).build());
		registerItem(new ItemCreator(Material.BOOK, 3).build());
	}

}
