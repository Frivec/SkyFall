package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Sniper extends Kit {
	
	public Sniper() {
		super("Sniper", 7, 5000, new ItemCreator(Material.BOW, 1).setDisplayName(fileManager.getLine("kits.sniper.name")).setLores(fileManager.getStringList("kits.sniper.description")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.BOW, 1).setDisplayName("§aSniper").setDurability((short) 382).build());
		registerItem(new ItemCreator(Material.INK_SACK, 3, (short) 6).setDisplayName("§aMunitions de Sniper").build());
	}
	
}
