package fr.frivec.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.frivec.SkyFall;
import fr.frivec.core.maps.SfMap;
import fr.frivec.utils.creators.ItemCreator;
import net.md_5.bungee.api.ChatColor;

public class VoteMapMenu {
	
	
	public static void openInventory(Player player) {
		
		final Inventory inventory = Bukkit.createInventory(null, 9, "§aVoter pour une carte");
		
		if(SkyFall.getInstance().getMapsLoaded().size() <= 9) {
			
			for(int i = 0; i < SkyFall.getInstance().getMapsLoaded().size(); i++) {
				
				final SfMap map = SkyFall.getInstance().getMapsLoaded().get(i);
				final String name = map.getWorld().getName();
				ItemStack itemStack = null;
				
				if(name.equals("Seasons"))
					itemStack = new ItemCreator(Material.PAPER, 1).setDisplayName(ChatColor.GREEN + name).setLores(new String[] {"§6Carte créée par Galix et Frivec", "", "§3Votes: §7" + map.getVotes()}).build();
				else if(name.equals("Ruines"))
					itemStack = new ItemCreator(Material.PAPER, 1).setDisplayName(ChatColor.GREEN + name).setLores(new String[] {"§6Carte créée par Teslon", "", "§3Votes: §7" + map.getVotes()}).build();
				
				inventory.setItem(i, itemStack);
			}
			
		}
		
		player.openInventory(inventory);
		
	}

}
