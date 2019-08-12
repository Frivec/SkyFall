package fr.frivec.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.database.utils.DataUtils;
import fr.frivec.core.game.States;
import fr.frivec.core.kits.Archer;
import fr.frivec.core.kits.Demolisseur;
import fr.frivec.core.kits.Enchanteur;
import fr.frivec.core.kits.Guerrier;
import fr.frivec.core.kits.Pionnier;
import fr.frivec.core.kits.Seguin;
import fr.frivec.core.kits.Sniper;
import fr.frivec.core.kits.model.Kit;
import fr.frivec.core.maps.SfMap;
import fr.frivec.core.runnables.LobbyRunnable;
import fr.frivec.utils.creators.NMSUtils;
import net.minecraft.server.v1_11_R1.EnumParticle;

public class DevCommand implements CommandExecutor {
	
	public static int count = 3;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			final Player player = (Player) sender;
			
			if(player.hasPermission("skyfall.dev")) {
				
				if(args.length == 0) {
					player.sendMessage("§cMerci d'indiquer un argument.");
					return true;
				}else if(args.length == 1) {
					
					if(args[0].equalsIgnoreCase("start")) {
						
						if(States.isState(States.WAIT)) {
							
							if(!LobbyRunnable.start) {
								
								if(!LobbyRunnable.forceStart) {
									
									LobbyRunnable.forceStart = true;
									LobbyRunnable.timer = 10;
									new LobbyRunnable().runTaskTimer(SkyFall.getInstance(), 0, 20);
									
									player.sendMessage("§aDémarrage forcé avec succès !");
									return false;
									
								}else {
									player.sendMessage("§cLe démarrage forcé a déjà été démarré.");
									return true;
								}
								
							}else {
								player.sendMessage("§cLe démarrage est déjà lancé.");
								return true;
							}
							
						}else {
							player.sendMessage("§cLe jeu n'est pas en statut d'attente.");
							return true;
						}
						
					}
					
					/*if(SkyFall.isActivated)
						player.sendMessage("§aLe plugin est déjà activé.");
					else {
						
						if(args[0].equalsIgnoreCase(FileManager.word)) {
							player.sendMessage("§aPlugin activé ! Bienvenue Frivec !");
							SkyFall.isActivated = true;
						}else {
							player.sendMessage("§cMauvais mot de passe.");
							
							if(count != 0) {
								player.sendMessage("§cIl vous reste " + count + " essais pour activer le plugin.");
								count--;
							}else {
								player.sendMessage("§4§lVous vous êtes trompé dans le mot de passe. Merci de rendre le plugin à son propriétaire ou de lui demander son autorisation pour l'utiliser.");
								Bukkit.getServer().getConsoleSender().sendMessage("§cVous utilisez un plugin ne vous appartenant pas ! le serveur va s'éteindre.");
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(SkyFall.getInstance(), new Runnable() {
									
									@Override
									public void run() {
										
										Bukkit.getServer().shutdown();
										
									}
								}, 5*20L);
							}
							
						}
						
					}*/
				
				}else if(args.length == 2) {
					
					if(args[0].equalsIgnoreCase("loadmap")){
						
						if(args[1] != null) {
							
							final World world = Bukkit.getWorld(args[1]);
							
							if(world != null) {
								
								for(SfMap maps : SkyFall.getInstance().getMapsLoaded()) {
									
									if(maps.getWorld().equals(world)) {
										
										maps.loadLocations();
										
									}
									
								}
								
							}
							
						}
						
					}
					
					if(args[0].equalsIgnoreCase("database")) {
						
						if(args[1].equalsIgnoreCase("coins")) {
							
							player.sendMessage("§aTest en cours des coins sur la base de donnée.");
							
							SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getStats().setCoinsWon(12);
							DataUtils.updateStats(player);
							SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getStats().setCoinsWon(0);
							
							player.sendMessage("§aLa mise à jour a été effectuée. Vous pouvez aller vérifier en bdd si l'envoie à bien été fait.");
							
						}
						
					}
					
					if(args[0].equalsIgnoreCase("worldtp")) {
						
						if(args[1] != null) {
							
							final World world = Bukkit.getWorld(args[1]);
							final Location location = player.getLocation();
							
							if(world != null) {
								
								location.setWorld(world);
								player.teleport(location);
								player.sendMessage("§aTéléportation au monde dans le monde §6" + world.getName() + " §a!");
								
							}else {
								player.sendMessage("§cLe monde " + args[1] + " n'existe pas !");
								return true;
							}
							
						}
						
					}
					
					if(args[0].equalsIgnoreCase("kits")) {
						
						Kit kit = null;
						
						if(args[1].equalsIgnoreCase("guerrier")) {
							
							kit = new Guerrier();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}else if(args[1].equalsIgnoreCase("archer")) {
							
							kit = new Archer();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}else if(args[1].equalsIgnoreCase("demolisseur")) {
							
							kit = new Demolisseur();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}else if(args[1].equalsIgnoreCase("enchanteur")) {
							
							kit = new Enchanteur();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}else if(args[1].equalsIgnoreCase("pionnier")) {
							
							kit = new Pionnier();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}else if(args[1].equalsIgnoreCase("seguin")) {
							
							kit = new Seguin();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}else if(args[1].equalsIgnoreCase("sniper")) {
							
							kit = new Sniper();
							kit.giveKit(player);
							player.getInventory().addItem(kit.getIcon());
							
						}
						
					}else if(args[0].equalsIgnoreCase("state")) {
						
						if(args[1].equals("game")) 
							States.setState(States.GAME);
						else if(args[1].equals("wait"))
							States.setState(States.WAIT);
						else if(args[1].equals("finish"))
							States.setState(States.FINISH);
						
					}else if(args[0].equalsIgnoreCase("particles")) {
						
						if(args[1].equals("fire")) {
							
							new BukkitRunnable(){
								
								Location loc = player.getLocation();
								double t = 0;
								double r = 2;
								public void run(){
									
									t += Math.PI/16;
									double x = r*Math.cos(t);
									double y = r*Math.sin(t);
									double z = r*Math.sin(t);
									loc.add(x, y, z);
									NMSUtils.spawnParticles(loc, player, EnumParticle.DRIP_WATER);
									loc.subtract(x, y, z);
									if (t > Math.PI*8)
										this.cancel();
									
								}
			                }.runTaskTimer(SkyFall.getInstance(), 0, 1);
						}
						
					}
					
				}
				
			}else {
				player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande. SACRIPANT !");
				return true;
			}
			
		}
		
		return false;
	}

}
