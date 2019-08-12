package fr.frivec.core.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.frivec.utils.creators.ItemCreator;

public class ChestLoader {
	
	public static List<Block> startChests = new ArrayList<>(), interChests = new ArrayList<>(), middleChests = new ArrayList<>();
	
	private enum ChestTypes{
		
		START(),
		INTER(),
		MIDDLE();
		
	}
	
	public static void fillChests() {
		
		for(Block blocks : startChests) {
							
			final Chest chest = (Chest) blocks.getState();
			final Inventory inventory = chest.getInventory();
						
			for(int i = 0; i < 5; i++) {
				
				final Random random = new Random();
				final int randomSlot = random.nextInt(inventory.getSize() - 1);
				final ItemStack item = generateRandomItem(ChestTypes.START);
				
				if(inventory.getItem(randomSlot) == null)
					inventory.setItem(randomSlot, item);
							
			}
			
		}
		
		for(Block blocks : interChests) {
			
			final Chest chest = (Chest) blocks.getState();
			final Inventory inventory = chest.getInventory();
						
			for(int i = 0; i < 5; i++) {
				
				final Random random = new Random();
				final int randomSlot = random.nextInt(inventory.getSize() - 1);
				final ItemStack item = generateRandomItem(ChestTypes.INTER);
				
				if(inventory.getItem(randomSlot) == null)
					inventory.setItem(randomSlot, item);
							
			}
			
		}
		
		for(Block blocks : middleChests) {
			
			final Chest chest = (Chest) blocks.getState();
			final Inventory inventory = chest.getInventory();
						
			for(int i = 0; i < 6; i++) {
				
				final Random random = new Random();
				final int randomSlot = random.nextInt(inventory.getSize() - 1);
				final ItemStack item = generateRandomItem(ChestTypes.MIDDLE);
				
				if(inventory.getItem(randomSlot) == null)
					inventory.setItem(randomSlot, item);
				else
					continue;
							
			}
			
		}
		
	}
	
	private static ItemStack generateRandomItem(ChestTypes type) {
		
		final Random random = new Random();
		ItemStack item = null;
		
		final int i = random.nextInt(100);
		
		if(type.equals(ChestTypes.START)) {
						
			if(i > 0 && i <= 20)
				item = new ItemStack(Material.STONE, 32);
			else if(i > 20 && i <= 40)
				item = new ItemStack(Material.WOOD, 32);
			else if(i > 40 && i <= 52)
				item = new ItemStack(Material.WOOD_SWORD);
			else if(i > 52 && i <= 58)
				item = new ItemStack(Material.GOLD_CHESTPLATE);
			else if(i > 58 && i <= 63)
				item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			else if(i > 63 && i <= 72)
				item = new ItemStack(Material.GOLD_SWORD);
			else if(i > 72 && i <= 80)
				item = new ItemStack(Material.SNOW_BALL, 8);
			else if(i > 80 && i <= 83)
				item = new ItemStack(Material.GOLD_HELMET);
			else if(i > 83 && i <= 85)
				item = new ItemStack(Material.CHAINMAIL_BOOTS);
			else if(i > 85 && i <= 88)
				item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			else if(i > 88 && i <= 90)
				item = new ItemCreator(Material.DEAD_BUSH, 1).setDisplayName("§cBad luck :c").setLores(new String[] {"§cTu n'as vraiment pas de chance :c", "§cC'est un vieux gag. Oui je sais ce n'est pas drôle."}).build();
			else if(i > 90 && i <= 95)
				item = new ItemStack(Material.STONE_PICKAXE);
			else if(i > 95 && i <= 100)
				item = new ItemStack(Material.COOKED_BEEF, 16);
			
		}else if(type.equals(ChestTypes.INTER)) {
						
			if(i >= 0 & i <= 25)
				item = new ItemStack(Material.STONE, 16);
			else if(i > 25 && i <= 45)
				item = new ItemStack(Material.COOKED_BEEF, 8);
			else if(i > 45 && i <= 48)
				item = new ItemCreator(Material.FLINT_AND_STEEL, 1).setDurability((short) (new ItemStack(Material.FLINT_AND_STEEL).getDurability() - 3)).build();
			else if(i > 48 && i <= 51)
				item = new ItemStack(Material.IRON_SWORD);
			else if(i > 51 && i <= 60)
				item = new ItemStack(Material.CHAINMAIL_HELMET);
			else if(i > 60 && i <= 65)
				item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			else if(i > 65 && i <= 68)
				item = new ItemStack(Material.IRON_BOOTS);
			else if(i > 68 && i <= 70)
				item = new ItemStack(Material.GOLDEN_APPLE);
			else if(i > 70 && i <= 74)
				item = new ItemStack(Material.IRON_SWORD);
			else if(i > 74 && i <= 75)
				item = new ItemCreator(Material.DEAD_BUSH, 1).setDisplayName("§cBad luck :c").setLores(new String[] {"§cTu n'as vraiment pas de chance :c", "§cC'est un vieux gag. Oui je sais ce n'est pas drôle."}).build();
			else if(i > 75 && i <= 80)
				item = new ItemStack(Material.SNOW_BALL, 8);
			else if(i > 80 && i <= 83)
				item = new ItemStack(Material.STONE_SWORD);
			else if(i > 83 && i <= 93)
				item = new ItemStack(Material.STONE_PICKAXE);
			else if(i > 93 && i <= 95)
				item = new ItemStack(Material.WATER_BUCKET);
			else if(i > 95 && i <= 96)
				item = new ItemCreator(Material.DIAMOND_SPADE, 1).setDisplayName("§bUne pelle spéciale").setLores(new String[] {"§aUne pelle spéciale"}).addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 5).build();
			else if(i > 96 && i <= 98)
				item = new ItemStack(Material.ENDER_PEARL);
			
		}else if(type.equals(ChestTypes.MIDDLE)) {
						
			/*
			 * items:
			 * diamond_helmet 3
			 * diamond_ sword 2
			 * iron_chestplate 13
			 * bow 5
			 * diamond_leggings 1
			 * golden_apple 9
			 * wood x32 25
			 * lava_bucket 10
			 * stone_sword fireaspect 1
			 * cooked_beef x8 14
			 * une epee tré speciale 1% (double random) 1%
			 * stickulator 3%
			 * 
			 */
			
			if(i >= 0 && i <= 5)
				item = new ItemStack(Material.DIAMOND_HELMET);
			else if(i > 5 && i <= 10)
				item = new ItemStack(Material.DIAMOND_SWORD);
			else if(i > 10 && i <= 23)
				item = new ItemStack(Material.IRON_CHESTPLATE);
			else if(i > 23 && i <= 29)
				item = new ItemStack(Material.DIAMOND_LEGGINGS);
			else if(i > 29 && i <= 35)
				item = new ItemStack(Material.GOLDEN_APPLE);
			else if(i > 35 && i <= 50)
				item = new ItemStack(Material.WOOD, 8);
			else if(i > 50 && i <= 60)
				item = new ItemStack(Material.LAVA_BUCKET);
			else if(i > 60 && i <= 62)
				item = new ItemCreator(Material.STONE_SWORD, 1).addEnchantment(Enchantment.FIRE_ASPECT, 1).build();
			else if(i > 62 && i <= 76)
				item = new ItemStack(Material.COOKED_BEEF, 8);
			else if(i > 76 && i <= 79)
				item = new ItemCreator(Material.STICK, 1).setDisplayName("§dStickulator").addUnsafeEnchantment(Enchantment.KNOCKBACK, 3).build();
			else if(i > 79 && i <= 80) {
				
				final Random r2 = new Random();
				final int y = r2.nextInt(100);
				
				if(y == 1)
					item = new ItemCreator(Material.DIAMOND_SWORD, 1).setDisplayName("§3Une épée encore plus spéciale !").setLores(new String[] {"§5§oTu peux te vanter d'être chanceux !", "§5§oPrends un screen et envoie-le moi stv :p"}).addEnchantment(Enchantment.DAMAGE_ALL, 3).addEnchantment(Enchantment.FIRE_ASPECT, 1).build();
				
			}else if(i > 80 && i <= 89)
				item = new ItemStack(Material.ARROW, 2);
			else if(i > 89 && i <= 92)
				item = new ItemStack(Material.BOW);
			
		}
		
		return item;
		
	}

}
