package fr.frivec.listeners.inventory;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;
import fr.frivec.core.kits.model.Kit;
import fr.frivec.core.kits.model.KitManager;
import fr.frivec.core.maps.SfMap;
import fr.frivec.utils.Sounds;
import fr.frivec.utils.creators.ItemCreator;
import fr.frivec.utils.inventory.SpecMenu;
import fr.frivec.utils.inventory.VoteMapMenu;

public class InventoryListeners implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		
		if(States.isState(States.WAIT))
			e.setCancelled(true);
		
	}
	
	//Vote start event !
	@EventHandler
	public void onVoteStart(PlayerInteractEvent e) {
		
		final Player player = e.getPlayer();
		final ItemStack item = e.getItem();
		final Action action = e.getAction();
		
		final ItemStack waitingStart = new ItemCreator(Material.SLIME_BALL, 1).setDisplayName("§aCommencer la partie").setLores(new String[] {"§5§oVotez pour commencer la partie."}).build();
		//final ItemStack confirmStart = new ItemCreator(Material.FIREBALL, 1).setDisplayName("§cEn attente des autres joueurs").setLores(new String[] {"§5§oVous avez voté pour le démarrage de la partie.", "§5§oEn attente des autres joueurs"}).build();
		
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			
			if(item != null && item.equals(waitingStart)) {
				
				player.sendMessage("§cCe mode est en développement. Merci de patienter.");
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		final Player player = e.getPlayer();
		final ItemStack itemStack = e.getItem();
		final Action action = e.getAction();
		
		if(itemStack != null && States.isState(States.WAIT)) {
			
			if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				
				if(itemStack.hasItemMeta() && itemStack.getItemMeta().getDisplayName().equals("§aSélecteur de kit")) {
					
					player.closeInventory();
					SkyFall.getInstance().getMenuManager().getVirtualMenu(1).openInventory(player);
					
				}else if(itemStack.hasItemMeta() && itemStack.getItemMeta().getDisplayName().contains("Voter")) {
					
					player.closeInventory();
					VoteMapMenu.openInventory(player);
					
				}
				
			}
			
		}else if(States.getState() != States.WAIT && itemStack != null && itemStack.getType().equals(Material.COMPASS) && action.equals(Action.RIGHT_CLICK_BLOCK) && Teams.getPlayerTeam(player).equals(Teams.SPEC)) {
			
			player.closeInventory();
			player.openInventory(SpecMenu.registerInventory());
			
		}
		
	}
	
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) {
		
		final Inventory inventory = e.getClickedInventory();
		final Player player = (Player)e.getWhoClicked();
		final ItemStack item = e.getCurrentItem();
		
		if(States.isState(States.WAIT)) {
			
			e.setCancelled(true);
		
			if(inventory != null && inventory.getTitle().equals("§aKits")) {
				
				if(item != null) {
					
					for(Entry<KitManager, Kit> entrys : KitManager.kits.entrySet()) {
						
						if(entrys.getValue().getIcon().getType().equals(item.getType())) {
							
							if(SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit() != null && entrys.getValue() == SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit()) {
								player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.wait.kits.alreadyChoose"));
								player.closeInventory();
								return;
							}
							
							KitManager.removePlayerKit(player.getUniqueId());
							KitManager.setPlayerKit(player.getUniqueId(), entrys.getKey());
							
							player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.wait.kits.chooseKit", player, entrys.getValue(), 0));
							player.closeInventory();
							
						}
						
					}
					
				}
				
			}else if(inventory != null && inventory.getTitle().contains("Vote")){
				
				if(item != null && item.getType().equals(Material.PAPER)) {
					
					final String mapName = item.getItemMeta().getDisplayName().substring(1).substring(1);
					
					System.out.println(mapName);
					
					for(SfMap map : SkyFall.getInstance().getMapsLoaded()) {
						
						if(map.getWorld().getName().equals(mapName)) {
							
							final SkyFallPlayer fallPlayer = SkyFallPlayer.getPlayerByUUID(player.getUniqueId());
							
							if(fallPlayer.getVotedMap() != null && fallPlayer.getVotedMap().equals(map)) {
								
								player.sendMessage("§cVous avez déjà voté pour cette carte.");
								player.closeInventory();
								new Sounds(player).playSound(Sound.ENTITY_VILLAGER_NO);
								return;
								
							}else {
								
								double votePoints = 1.0;
								
								if(fallPlayer.getVotedMap() != null)
									fallPlayer.getVotedMap().setVotes(fallPlayer.getVotedMap().getVotes() - votePoints);
								
								fallPlayer.setVotedMap(map);
								map.setVotes(map.getVotes() + votePoints);
								
								Bukkit.broadcastMessage("§" + fallPlayer.getColor() + player.getName() + " §aa voté pour la carte §6" + mapName + "§a.");
								
								player.closeInventory();
								
							}
							
						}
						
					}
				}
				
			}else {
				
				if(item != null && item.getType().equals(Material.NETHER_STAR)) {
					
					player.closeInventory();
					SkyFall.getInstance().getMenuManager().getVirtualMenu(1).openInventory(player);
					
				}
				
			}
			
		}else {
			
			if(inventory != null && inventory.getTitle().equals("§aTéléportation")) {
				
				if(item != null) {
					
					final Player target = Bukkit.getPlayer(item.getItemMeta().getDisplayName());
					
					player.teleport(target);
				}
				
			}else if(item != null && item.getType().equals(Material.COMPASS) && Teams.getPlayerTeam(player).equals(Teams.SPEC)) {
				
				player.closeInventory();
				player.openInventory(SpecMenu.registerInventory());
				
			}
			
		}
	}

}
