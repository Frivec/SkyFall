package fr.frivec.listeners.kits;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.States;
import fr.frivec.core.kits.model.Kit;
import fr.frivec.core.kits.model.KitManager;

public class KitListeners implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		final Player player = e.getPlayer();
		final ItemStack item = e.getItem();
		final Action action = e.getAction();
		
		if(States.isState(States.GAME)) {
			
			final Kit kit = SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit();
				
			if(item != null && item.getType().equals(Material.BOW) && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§aSniper")) {
				
				if(KitManager.getKitManagerByKit(kit).equals(KitManager.SNIPER)) {
					
					if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
							
						if(player.hasPotionEffect(PotionEffectType.SLOW))
							player.removePotionEffect(PotionEffectType.SLOW);
						else 
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 12, false, false));
							
					}else if(action.equals(Action.RIGHT_CLICK_AIR)) {
						
						for(ItemStack itemStack : player.getInventory().getContents()) {
							
							if(itemStack != null && itemStack.getType().equals(Material.INK_SACK) && player.getEyeLocation().getBlock().getType().equals(Material.AIR)) {
								
								int amount = itemStack.getAmount() - 1;
								
								player.launchProjectile(Arrow.class);
								
								if(amount != 0)
									itemStack.setAmount(amount);
								else
									player.getInventory().remove(itemStack);
								
								player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
								
								player.updateInventory();
								
							}
							
						}
							
					}
						
				}else {
					player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.cannotUseSniper"));
					return;
				}
					
			}else if(item != null && item.getType().equals(Material.TNT)) {
				
				if(KitManager.getKitManagerByKit(kit).equals(KitManager.DEMOLISSEUR)) {
					
					final TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
					tnt.setCustomName("§cWarning !");
					tnt.setCustomNameVisible(true);
					tnt.setGlowing(true);
					tnt.setFuseTicks(15);
					
				}else {
					player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.cannotUseTNT"));
					e.setCancelled(true);
					return;
				}
				
			}
				
		}
		
	}
	
	@EventHandler
	public void onLaunchProjectile(ProjectileLaunchEvent e) {
		
		final Projectile projectile = e.getEntity();
		
		if(projectile instanceof Arrow && projectile.getShooter() instanceof HumanEntity) {
				
			final Player player = (Player) projectile.getShooter();
			
			if(KitManager.getKitManagerByKit(SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit()).equals(KitManager.SNIPER)) {
					
				((Arrow) projectile).setPickupStatus(PickupStatus.CREATIVE_ONLY);
				projectile.setGravity(false);
				((Arrow) projectile).setKnockbackStrength(1);
				projectile.setCustomName("§cBam ! En pleine tête !");
				projectile.setCustomNameVisible(true);
				projectile.getVelocity().multiply(2);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		final Block block = e.getBlock();
		final Player player = e.getPlayer();
		
		if(block.getType().equals(Material.TNT)) {
			
			if(KitManager.getKitManagerByKit(SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit()).equals(KitManager.DEMOLISSEUR)) {
				
				block.setType(Material.AIR);
				
				final TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
				tnt.setCustomName("§cWarning !");
				tnt.setCustomNameVisible(true);
				tnt.setGlowing(true);
				tnt.setFuseTicks(25);
				
			}else {
				player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.cannotUseTNT"));
				e.setCancelled(true);
				return;
			}
			
		}
		
	}

}
