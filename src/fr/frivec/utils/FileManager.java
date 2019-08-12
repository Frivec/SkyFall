package fr.frivec.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.frivec.SkyFall;
import fr.frivec.core.kits.model.Kit;

public class FileManager {
	
	private SkyFall main;
	
	public static String word = "Gm@ngédP0mes";
	
	private File file;
	private FileConfiguration messages;
	
	public FileManager(SkyFall main) {
		this.main = main;
		
		createFiles();
		
	}
	
	private void createFiles() {
		
		if(!this.main.getDataFolder().exists())
			this.main.getDataFolder().mkdirs();
		
		this.file = new File(this.main.getDataFolder(), "messagesFR.yml");
		
		if(!this.file.exists()) {
			
			try (InputStream in = SkyFall.class.getClassLoader().getResourceAsStream("messages.yml")) {
				Files.copy(in, this.file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		this.setMessages(YamlConfiguration.loadConfiguration(this.file));
		
	}
	
	public void reloadMessages(){ this.messages = YamlConfiguration.loadConfiguration(this.file); }
	
	public String getLine(String path) {
		
		String string = "";
		
		string = this.messages.getString(path);
		string = string.replace("&", "§");
		
		return string;
	}
	
	public String getLine(String path, Player player, Player killer) {
		
		String string = "";
		
		string = getLine(path);
		
		string = string.replace("%player%", player.getName());
		if(killer != null) 
			string = string.replace("%killer%", killer.getName());
		
		return string;
	}
	
	public String getLine(String path, Player player, Kit kit, int timer) {
		
		String string = "";
		
		string = getLine(path);
		if(player != null)
			string = string.replace("%player%", player.getName());
		string = string.replace("%size%", main.getGame().getPlayers().size() + "");
		if(kit != null)
			string = string.replace("%kitname%", kit.getName());
		string = string.replace("%timer%", timer + "");
		
		return string;
	}
	
	public List<String> getStringList(String path) {
		
		final List<String> list = new ArrayList<>();
		
		for(String strings : this.messages.getStringList(path)) {
			
			strings = strings.replace("&", "§");
			
			list.add(strings);
		}
		
		return list;
	}
	
	public void reloadWorld() {
		
		final World world = main.getGame().getMap().getWorld();
		final String mapName = world.getName();
		
		Bukkit.getServer().unloadWorld("world", true);
		Bukkit.getServer().unloadWorld(world, false);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		deleteFile(world.getWorldFolder());
		
		final Path source = Paths.get("/home/maps/" + mapName + "/"), target = Paths.get("/home/SkyFall/" + mapName + "/");
		
		if(Files.exists(source))
			try {
				FileUtils.copyDirectory(source.toFile(), target.toFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	private static void deleteFile(File path) {

		if(path.exists()){
			File files[] = path.listFiles();
			
			for(int i = 0; i < files.length; i++){
				if(files[i].isDirectory()){
					deleteFile(files[i]);
				}else{
					files[i].delete();
				}
			}
			
		}
	}

	public FileConfiguration getMessages() {
		return messages;
	}

	public void setMessages(FileConfiguration messages) {
		this.messages = messages;
	}

}
