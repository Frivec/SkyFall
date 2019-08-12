package fr.frivec.core.kits.model;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import fr.frivec.SkyFall;
import fr.frivec.utils.FileManager;
import fr.frivec.utils.creators.ItemCreator;

public abstract class Kit {
	
	protected static FileManager fileManager = SkyFall.getInstance().getFileManager();
	
	protected String name;
	protected int price;
	protected ItemStack icon;
	protected int slot;
	protected List<ItemStack> items;
	
	public Kit(String name, int slot, int price, ItemStack icon) {
		this.name = name;
		this.slot = slot;
		this.price = price;
		this.icon = icon;
		this.items = new ArrayList<>();
		
		registerKitItems();
	}
	
	public abstract void registerKitItems();
	
	public void registerItem(ItemStack itemStack) {
		this.items.add(itemStack);
	}
	
	public void giveKit(Player player) {
		
		final ItemStack item = new ItemCreator(Material.LIGHT_BLUE_SHULKER_BOX, 1).setDisplayName("§6Kit " + name).setLores(new String[] {"§5§oCette boite contient votre équipement.", "§5§oOuvrez-la une fois sur terre."}).build();
		
		if(item.getItemMeta() instanceof BlockStateMeta) {
		
			final BlockStateMeta blockState = (BlockStateMeta) item.getItemMeta();
		
			if(blockState.getBlockState() instanceof ShulkerBox) {
				
				final ShulkerBox box = (ShulkerBox) blockState.getBlockState(); 
				final Inventory inventory = box.getInventory();
				
				for(int i = 0; i < items.size(); i++)
					inventory.addItem(items.get(i));
				
				blockState.setBlockState(box);
				item.setItemMeta(blockState);
				
				player.getInventory().addItem(item);
				
			}
			
		}
		
	}
	
	public int getSlot() {
		return slot;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getIcon() {
		return this.icon;
	}
		
}
