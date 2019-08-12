package fr.frivec.utils.inventory.abstracts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class VirtualMenu {
	
	protected String name;
	protected int lines;
	
	protected Map<Integer, ItemStack> items;
	
	public VirtualMenu(String name, int lines) {
		this.name = name;
		this.lines = lines;
		this.items = new HashMap<>();
		registerItems();
	}
	
	public abstract void registerItems();
	
	public void registerItem(int slot, ItemStack itemStack) {
		
		if(this.items.containsKey(slot))
			this.items.remove(slot);
		
		this.items.put(slot, itemStack);
		
	}
	
	public void openInventory(Player player) {
		
		Inventory inventory = Bukkit.createInventory(null, lines * 9, this.name);
		
		for(Entry<Integer, ItemStack> entrys : this.items.entrySet()) {
			inventory.setItem(entrys.getKey(), entrys.getValue());
		}
		
		player.closeInventory();
		player.openInventory(inventory);
		
	}

}
