package fr.frivec.core.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.utils.creators.ItemCreator;

public class Demolisseur extends Kit {
	
	public Demolisseur() {
		super("D�molisseur", 2, 5000, new ItemCreator(Material.TNT, 1).addEnchantment(Enchantment.DAMAGE_ALL, 1).setDisplayName(fileManager.getLine("kits.demolisseur.name")).setLores(fileManager.getStringList("kits.demolisseur.description")).build());
	}
	
	@Override
	public void registerKitItems() {
		registerItem(new ItemCreator(Material.TNT, 3).setDisplayName("�cTNT instantann�es").build());
		registerItem(new ItemCreator(Material.SHIELD, 1).setDisplayName("�bBouclier anti-explosions").setDurability((short) 334).build());
	}
	
	//D�tecter quand un joueur re�oit des d�gats et s'il est en train de bloquer avec un bouclier renomm� et qu'il poss�de le kit d�molisseur.

}
