package fr.frivec.listeners.server;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;
import fr.frivec.core.kits.model.KitManager;

public class UtilsListeners implements Listener {
	
	@EventHandler
	public void serverListPing(ServerListPingEvent e) {
		
		if(States.isState(States.WAIT))
			e.setMotd("               §3§lSkyfall §b| §6Version 1.11 bêta                     §aEn attente de joueurs. Vous pouvez rejoindre !");//45 caractères par ligne
		else if(States.isState(States.NODAMAGE) || States.isState(States.GAME))
			e.setMotd("               §3§lSkyFall §b| §6Version 1.11 bêta                                   §cEn jeu | Spectateurs acceptés");
		else if(States.isState(States.FINISH))
			e.setMotd("               §3§lSkyFall §b| §6Version 1.11 bêta                         §9Partie terminée. Redémarrage en cours !");
		
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if(States.isState(States.WAIT))
			e.setCancelled(true);
	}
	
	//set the chat format
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		final Player player = e.getPlayer();
		final String message = e.getMessage();
		final SkyFallPlayer skyfallPlayer = SkyFallPlayer.getPlayerByUUID(player.getUniqueId());
		
		e.setCancelled(true);
		
		if(skyfallPlayer.getTag().contains("Joueur"))
			Bukkit.broadcastMessage("§" + skyfallPlayer.getColor() + player.getName() + ": " + message);
		else
			Bukkit.broadcastMessage("§" + skyfallPlayer.getColor() + "[" + skyfallPlayer.getTag() + "] " + player.getName() + "§f : " + message.replace("&", "§"));
		
	}
	
	//check damages for the "demolisseur" kit.
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		
		final DamageCause cause = e.getCause();
			
		if(cause.equals(DamageCause.BLOCK_EXPLOSION) || cause.equals(DamageCause.ENTITY_EXPLOSION)) {
			
			if(e.getEntity() instanceof HumanEntity) {
			
				final Player player = (Player) e.getEntity();
				final KitManager kit = KitManager.getKitManagerByKit(SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit());
				
				if(player.isBlocking() && kit.equals(KitManager.DEMOLISSEUR) && (player.getInventory().getItemInOffHand().getType().equals(Material.SHIELD) || player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD))) {
						
					e.setDamage(e.getDamage() / 2);
					player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.demoProtect"));
						
				}
			}
		}
		
	}
	
	//Break blocks placed by a player after 4 minutes.
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		final Block block = e.getBlock();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SkyFall.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				if(block != null)
					block.setType(Material.AIR);
				
			}
			
		}, 20*(4*60));
		
	}
	
	//Break the chest block when closed
	
	@EventHandler
	public void onChestClose(InventoryCloseEvent e) {
		
		final Inventory inventory = e.getInventory();
		final Player player = (Player) e.getPlayer();
		
		if(inventory.getType().equals(InventoryType.CHEST) && States.isState(States.GAME) && Teams.getPlayerTeam(player).equals(Teams.PLAYERS)) {
			
			System.out.println("chest inventory closed");
			
			final Chest chest = (Chest) inventory.getHolder();
				
			Iterator<ItemStack> drops = chest.getBlock().getDrops().iterator();
			
			while(drops.hasNext())
				
				if(drops.next().getType().equals(Material.CHEST)) {
					System.out.println("chest in drops");
					drops.remove();
				}
			
			chest.getBlock().breakNaturally();
			
		}
		
	}

}
