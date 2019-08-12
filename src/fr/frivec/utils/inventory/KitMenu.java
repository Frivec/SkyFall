package fr.frivec.utils.inventory;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import fr.frivec.core.kits.model.Kit;
import fr.frivec.core.kits.model.KitManager;
import fr.frivec.utils.creators.ItemCreator;
import fr.frivec.utils.inventory.abstracts.VirtualMenu;

public class KitMenu extends VirtualMenu {
	
	public KitMenu() {
		super("§aKits", 1);
	}
	
	@Override
	public void registerItems() {
		
		for(int j = 0; j < 9; j++)
			registerItem(j, new ItemCreator(Material.STAINED_GLASS_PANE, 1, (short) 7).setDisplayName("").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
		
		for(Entry<KitManager, Kit> entrys : KitManager.kits.entrySet()) {
			
			final Kit kit = entrys.getValue();
			
			registerItem(kit.getSlot(), kit.getIcon());
		}
		
	}

}
