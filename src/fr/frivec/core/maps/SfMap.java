package fr.frivec.core.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.frivec.SkyFall;
import fr.frivec.utils.Cuboid;

public class SfMap {
	
	private double votes;
	
	private Location center;
	private World world;
	private int islandsDestroyes;
	private List<Location> starts;
	private List<Location> secondIslands;
	private List<Location> islandsUsed;
	
	public SfMap(World world) {
		this.setWorld(world);
		this.votes = 0.0;
		this.starts = new ArrayList<>();
		this.secondIslands = new ArrayList<>();
		this.islandsUsed = new ArrayList<>();
		SkyFall.getInstance().getMapsLoaded().add(this);
	}
	
	public void addStartLocation(Location location) {
		this.starts.add(location);
	}
	
	public void removeStartLocation(Location location) {
		this.starts.remove(location);
	}
	
	public Location getLocationFromStarts(int index) {
		
		Location location = null;
		
		location = this.starts.get(index);
		
		return location;
	}
	
	public void addSecondLocation(Location location) {
		this.secondIslands.add(location);
	}
	
	public void removeSecondLocation(Location location) {
		this.secondIslands.remove(location);
	}
	
	public Location getLocationFromSecondIslands(int index) {
		
		Location location = null;
		
		location = this.secondIslands.get(index);
		
		return location;
	}
	
	public Location getCenter() {
		return center;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void destroyIslands() {
		
		if(this.islandsDestroyes == 0) {
						
			Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyFall.getInstance(), new Runnable() {
				
				int timer = 0;
				
				@Override
				public void run() {
					
					if(timer == 6)
						return;
					
					for(int i = 0; i < starts.size(); i++) {
						
						final Location location = starts.get(i);
						final Location fireballLocation = new Location(location.getWorld(), location.getX(), location.getY() + 10, location.getZ());
						
						final double dX = fireballLocation.getX() - location.getX();
						final double dY = fireballLocation.getY() - location.getY();
						final double dZ = fireballLocation.getZ() - location.getZ();
						
						Fireball fireball = (Fireball) world.spawnEntity(fireballLocation, EntityType.FIREBALL);
						fireball.setDirection(new Vector(dX, dY, dZ).multiply(-1));
												
						if(timer == 5){
						
							final Location firstLocation = new Location(location.getWorld(), location.getX() - 10, location.getY() + 11, location.getZ() - 10);
							final Location secondLocation = new Location(location.getWorld(), location.getX() + 10, location.getY() - 11, location.getZ() + 10);
							
							final Cuboid cuboid = new Cuboid(firstLocation, secondLocation);
							
							for(Block block : cuboid)
								if(!block.getType().equals(Material.AIR))
									block.setType(Material.AIR);
							
							getWorld().createExplosion(location, 5.0f);
							
						}
						
					}
					
					timer++;
					
				}
			}, 20, 20);
			
		}else if (this.islandsDestroyes == 1) {
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyFall.getInstance(), new Runnable() {
				
				int timer = 0;
				
				@Override
				public void run() {
					
					if(timer == 6)
						return;
					
					for(int i = 0; i < secondIslands.size(); i++) {
						
						final Location location = secondIslands.get(i);
						final Location fireballLocation = new Location(location.getWorld(), location.getX(), location.getY() + 10, location.getZ());
						
						final double dX = fireballLocation.getX() - location.getX();
						final double dY = fireballLocation.getY() - location.getY();
						final double dZ = fireballLocation.getZ() - location.getZ();
						
						Fireball fireball = (Fireball) world.spawnEntity(fireballLocation, EntityType.FIREBALL);
						fireball.setDirection(new Vector(dX, dY, dZ).multiply(-1));
						
						if(timer == 5){
						
							final Location firstLocation = new Location(location.getWorld(), location.getX() - 15, location.getY() + 16, location.getZ() - 15);
							final Location secondLocation = new Location(location.getWorld(), location.getX() + 15, location.getY() - 16, location.getZ() + 15);
							
							final Cuboid cuboid = new Cuboid(firstLocation, secondLocation);
							
							for(Block block : cuboid)
								if(!block.getType().equals(Material.AIR))
									block.setType(Material.AIR);
							
							getWorld().createExplosion(location, 5.0f);
							
						}
						
					}
					
					timer++;
					
				}
			}, 20, 20);
			
		}
		
		this.islandsDestroyes++;
		
	}
	
	private void teleportPlayer(final Player player) {
		
		final Random random = new Random();
		final int randomID = random.nextInt(this.starts.size());
		
		if(!this.islandsUsed.contains(this.starts.get(randomID))) {
			player.teleport(this.getLocationFromStarts(randomID));
			this.islandsUsed.add(this.getLocationFromStarts(randomID));
		}else
			teleportPlayer(player);
		
	}
	
	public void spawnPlayers() {
		
		for(int i = 0; i < SkyFall.getInstance().getGame().getPlayers().size(); i++) {
			
			final Player player = SkyFall.getInstance().getGame().getPlayers().get(i);
			
			teleportPlayer(player);
		}
		
	}
	
	public void loadLocations() {
		
		for(Chunk chunk : this.getWorld().getLoadedChunks()) {
			
			int bx = chunk.getX()<<4;
			int bz = chunk.getZ()<<4;

			for(int x = bx; x < bx+16; x++) {
			    for(int z = bz; z < bz+16; z++) {
			        for(int y = 0; y < 128; y++) {
			        	
						final Block block = world.getBlockAt(x, y, z);
						final Material material = block.getType();
								
						if(material.equals(Material.SPONGE)) {
							this.center = block.getLocation();
							block.setType(Material.AIR); 
						}else if(material.equals(Material.EMERALD_BLOCK)) {
							this.addStartLocation(block.getLocation());
							block.setType(Material.AIR);
						}else if(material.equals(Material.DIAMOND_BLOCK)) {
							this.addSecondLocation(block.getLocation());
							block.setType(Material.AIR);
						}else if(material.equals(Material.NETHER_WART_BLOCK)) {
							block.setType(Material.CHEST);
							ChestLoader.startChests.add(block);
						}else if(material.equals(Material.BRICK)) {
							block.setType(Material.CHEST);
							ChestLoader.interChests.add(block);
						}else if(material.equals(Material.END_BRICKS)) {
							block.setType(Material.CHEST);
							ChestLoader.middleChests.add(block);
						}
					
					}
					
				}
				
			}
			
		}
		
		ChestLoader.fillChests();
		
		System.out.println(this.starts.size());
		
	}
	
	public int getIslandsDestroyes() {
		return islandsDestroyes;
	}
	
	public double getVotes() {
		return votes;
	}
	
	public void setVotes(double votes) {
		this.votes = votes;
	}

}
