package fr.frivec.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.frivec.SkyFall;
import fr.frivec.core.database.utils.DataUtils;
import fr.frivec.core.game.StatsData;
import fr.frivec.core.kits.model.Kit;
import fr.frivec.core.maps.SfMap;

public class SkyFallPlayer {
	
	private UUID uuid;
	private Kit kit;
	private String tag;
	private int kills; //Stats since his first connection !
	private double coins;
	private int victories;
	private String color;
	private StatsData stats;
	private boolean isWinner;
	private boolean hasStart;
	private SfMap votedMap;
	
	private static Map<UUID, SkyFallPlayer> skyfallPlayers = new HashMap<>();
	
	public SkyFallPlayer(Player player) {
		
		this.setUuid(player.getUniqueId());
		
		String rank = "";
		String color = "";
		
		if(!skyfallPlayers.containsKey(uuid)) {
			
			if(SkyFall.getInstance().hasDatabase()) {
			
				if(!DataUtils.hasAccount(player.getUniqueId())) {
				
					//Disabled because of the Epicube's servers shutdown
	//				rank = SkyFall.getInstance().getManager().getInfo(player.getUniqueId(), "tag");
	//				
	//				color = SkyFall.getInstance().getManager().getInfo(player.getUniqueId(), "color");
	//				
	//				rank = rank.substring(1, rank.length() - 1);
	//				color = color.substring(1, color.length() - 1);
					
					DataUtils.createAccount(player, "", "");
					
				}else {
					
					rank = DataUtils.getInfoFromDatabase(player, "prefix");
					color = DataUtils.getInfoFromDatabase(player, "color");
					setKills(Integer.parseInt(DataUtils.getInfoFromDatabase(player, "kills")));
					setCoins(Double.parseDouble(DataUtils.getInfoFromDatabase(player, "coins")));
					setVictories(Integer.parseInt(DataUtils.getInfoFromDatabase(player, "victories")));
					
				}
				
			}else {
				
				setKills(0);
				setCoins(0);
				setVictories(0);
				
			}
			
			this.setStats(new StatsData(0, 0, 0));
			this.setTag(rank);
			this.setColor(color);
			this.setKills(0);
			this.setKit(null);
			this.setWinner(false);
			this.setHasStart(false);
			this.setVotedMap(null);
			
			skyfallPlayers.put(uuid, this);
			
		}
	}
	
	public StatsData getStats() {
		return stats;
	}
	
	public void setStats(StatsData stats) {
		this.stats = stats;
	}
	
	public void setKit(Kit kit) {
		this.kit = kit;
	}
	
	public Kit getKit() {
		return kit;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getVictories() {
		return victories;
	}

	public void setVictories(int victories) {
		this.victories = victories;
	}

	public double getCoins() {
		return coins;
	}

	public void setCoins(double coins) {
		this.coins = coins;
	}
	
	public static SkyFallPlayer getPlayerByUUID(UUID uuid) {
		return skyfallPlayers.get(uuid);
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public boolean hasStart() {
		return hasStart;
	}

	public void setHasStart(boolean hasStart) {
		this.hasStart = hasStart;
	}

	public SfMap getVotedMap() {
		return votedMap;
	}

	public void setVotedMap(SfMap votedMap) {
		this.votedMap = votedMap;
	}

}
