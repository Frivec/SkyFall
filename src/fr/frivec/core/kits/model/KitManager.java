package fr.frivec.core.kits.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.kits.Archer;
import fr.frivec.core.kits.Demolisseur;
import fr.frivec.core.kits.Enchanteur;
import fr.frivec.core.kits.Guerrier;
import fr.frivec.core.kits.Pionnier;
import fr.frivec.core.kits.Seguin;
import fr.frivec.core.kits.Sniper;

public enum KitManager {
	
	ARCHER(new Archer()),
	DEMOLISSEUR(new Demolisseur()),
	ENCHANTEUR(new Enchanteur()),
	GUERRIER(new Guerrier()),
	PIONNIER(new Pionnier()),
	SEGUIN(new Seguin()),
	SNIPER(new Sniper());
	
	private Kit kit;
	
	public static Map<KitManager, Kit> kits = new HashMap<>();
	
	private KitManager(Kit kit) {
		this.kit = kit;
	}
	
	static {
		for(KitManager kit : values()) {
			kits.put(kit, kit.kit);
		}
	}
	
	public static KitManager getKitByName(String name) {
		
		KitManager manager =  null;
		
		for(Entry<KitManager, Kit> entrys : kits.entrySet())
			
			if(entrys.getValue().getName().equalsIgnoreCase(name))
				manager = entrys.getKey();
		
		return manager;
	}
	
	public static KitManager getKitManagerByKit(Kit kit) {
		
		KitManager kitManager = null;
		
		for(Entry<KitManager, Kit> entrys : kits.entrySet())
			
			if(entrys.getValue().equals(kit))
				
				kitManager = entrys.getKey();
		
		return kitManager;
	}
	
	public static Kit getKitByKitManager(KitManager manager) {
		
		Kit kit = null;
		
		for(Entry<KitManager, Kit> entrys : kits.entrySet())
			
			if(entrys.getKey().equals(manager))
				kit = entrys.getValue();
		
		return kit;
	}
	
	public static void removePlayerKit(UUID uuid) {
		
		if(SkyFallPlayer.getPlayerByUUID(uuid) != null) {
			
			if(SkyFallPlayer.getPlayerByUUID(uuid).getKit() != null)
				SkyFallPlayer.getPlayerByUUID(uuid).setKit(null);
			
		}
		
	}
	
	public static void setPlayerKit(UUID uuid, KitManager manager) {
		
		if(SkyFallPlayer.getPlayerByUUID(uuid) != null) {
			
			if(SkyFallPlayer.getPlayerByUUID(uuid).getKit() == null) {
				
				removePlayerKit(uuid);
				SkyFallPlayer.getPlayerByUUID(uuid).setKit(getKitByKitManager(manager));
				
			}
		}
		
	}

}
