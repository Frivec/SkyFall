package fr.frivec.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.frivec.SkyFall;
import fr.frivec.utils.creators.ItemCreator;

public class SpecMenu {
	
	public static Inventory registerInventory() {
		
		final Inventory inventory = Bukkit.createInventory(null, 18, "§aTéléporation");
		
		for(int i = 0; i < SkyFall.getInstance().getGame().getPlayers().size(); i++) {
			
			final Player player = SkyFall.getInstance().getGame().getPlayers().get(i);
			
			inventory.setItem(i, new ItemCreator(Material.SKULL_ITEM, 1).skull(player.getName()).setDisplayName(player.getName()).build());
			
		}
		
		return inventory;
		
	}

}
