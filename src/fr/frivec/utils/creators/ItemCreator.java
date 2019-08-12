package fr.frivec.utils.creators;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemCreator {
	
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	public ItemCreator(Material material, int amount, short data) {
		this.itemStack = new ItemStack(material, amount, data);
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemCreator(Material material, int amount) {
		this.itemStack = new ItemStack(material, amount);
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemCreator(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemCreator skull(String owner) {
		
		final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		meta.setOwner(owner);
		itemStack.setItemMeta(meta);
		
		return new ItemCreator(itemStack);
		
	}
	
	public ItemCreator setAmount(int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}
	
	public ItemCreator setDisplayName(String name) {
		this.itemMeta.setDisplayName(name);
		return this;
	}
	
	public ItemCreator setDurability(short durability) {
		this.itemStack.setDurability(durability);
		return this;
	}
	
	public ItemCreator setLores(String... lores) {
		this.itemMeta.setLore(Arrays.asList(lores));
		return this;
	}
	
	public ItemCreator setLores(List<String> lores) {
		this.itemMeta.setLore(lores);
		return this;
	}
	
	public ItemCreator addUnsafeEnchantment(Enchantment enchantment, int level) {
		this.itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}
	
	public ItemCreator addEnchantment(Enchantment enchantment, int level) {
		this.itemMeta.addEnchant(enchantment, level, false);
		return this;
	}
	
	public ItemCreator addItemFlag(ItemFlag flag) {
		this.itemMeta.addItemFlags(flag);
		return this;
	}
	
	public ItemStack build() {
		this.itemStack.setItemMeta(itemMeta);
		return this.itemStack;
	}
	
}
