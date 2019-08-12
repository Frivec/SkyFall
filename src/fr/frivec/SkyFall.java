package fr.frivec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.frivec.commands.CommandsManager;
import fr.frivec.core.database.DatabaseManager;
import fr.frivec.core.database.utils.DataUtils;
import fr.frivec.core.game.Game;
import fr.frivec.core.game.States;
import fr.frivec.core.json.GsonManager;
import fr.frivec.core.maps.SfMap;
import fr.frivec.listeners.ListenerManager;
import fr.frivec.utils.FileManager;
import fr.frivec.utils.inventory.MenuManager;
import fr.frivec.utils.scoreboard.ScoreboardSign;

public class SkyFall extends JavaPlugin {
	
	/*
	 * Phase de tests avec des personnes que je ne connais pas:
	 * participants:
	 * MaitreFranck - Demande via le forum.
	 * 
	 * 
	 */
	
	private static SkyFall instance;
	
	private Map<Player, ScoreboardSign> boards;
	private List<SfMap> mapsLoaded;
	
	private Game game;
	private GsonManager manager;
	private FileManager fileManager;
	private MenuManager menus;
	
	private BossBar bossbar;
	private DatabaseManager databaseManager;
	
	private boolean database;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		saveDefaultConfig();
		this.fileManager = new FileManager(this);
		this.boards = new HashMap<>();
		this.mapsLoaded = new ArrayList<>();
		this.manager = new GsonManager();
		this.bossbar = Bukkit.createBossBar("§6§kyos§a En attente de joueurs §6§kslt", BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
		this.game = new Game(null);
		loadMaps();
		this.menus = new MenuManager();
		
		this.database = this.getConfig().getBoolean("database.enable");
		
		if(this.database) {
			
			this.databaseManager = new DatabaseManager();
			DataUtils.createTables();
			
		}
		
		new ListenerManager(this);
		new CommandsManager(this);
		
		States.setState(States.WAIT);
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		if(this.hasDatabase())
		
			this.databaseManager.close();
		
		if(this.game.getMap() != null)
		
			this.fileManager.reloadWorld();
		
		super.onDisable();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setBossbar(BossBar bossbar) {
		this.bossbar = bossbar;
	}
	
	public BossBar getBossbar() {
		return bossbar;
	}
	
	public GsonManager getManager() {
		return manager;
	}
	
	public static SkyFall getInstance() {
		return instance;
	}
	
	public Map<Player, ScoreboardSign> getBoards() {
		return boards;
	}

	public FileManager getFileManager() {
		return fileManager;
	}
	
	public MenuManager getMenuManager() {
		return menus;
	}
	
	public DatabaseManager getDatabaseManager() {
		return this.databaseManager;
	}
	
	public List<SfMap> getMapsLoaded() {
		return mapsLoaded;
	}
	
	public boolean hasDatabase() {
		return database;
	}
	
	private void loadMaps() {
		
		for(String string : this.getConfig().getStringList("maps")) {
			
			final World world = Bukkit.createWorld(new WorldCreator(string));
			
			if(world == null) {
				System.out.println("world " + string + " null");
				return;
			}
			
			for(int x = -9; x < 9; x++) {
				
				for(int z = -9; z < 9; z++) {
					
					final Chunk chunk = world.getChunkAt(x, z);
					chunk.load();
				}
				
			}
			
			System.out.println("Chunks loaded: " + world.getLoadedChunks().length);
			
			new SfMap(world);
			
		}
		
		System.out.println("Maps number: " + this.getMapsLoaded().size());
		
	}

}
