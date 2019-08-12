package fr.frivec.listeners;

import fr.frivec.SkyFall;
import fr.frivec.listeners.inventory.InventoryListeners;
import fr.frivec.listeners.kits.KitListeners;
import fr.frivec.listeners.player.PlayerDeathListener;
import fr.frivec.listeners.player.PlayerJoinListener;
import fr.frivec.listeners.player.PlayerMoveListener;
import fr.frivec.listeners.player.PlayerQuitListener;
import fr.frivec.listeners.server.UtilsListeners;

public class ListenerManager {
	
	private SkyFall main;
	
	public ListenerManager(SkyFall main) {
		this.main = main;
		registerListeners();
	}
	
	void registerListeners() {
		main.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), main);
		main.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), main);
		main.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), main);
		main.getServer().getPluginManager().registerEvents(new InventoryListeners(), main);
		main.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), main);
		main.getServer().getPluginManager().registerEvents(new KitListeners(), main);
		main.getServer().getPluginManager().registerEvents(new UtilsListeners(), main);
	}

}
