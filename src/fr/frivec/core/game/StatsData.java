package fr.frivec.core.game;

public class StatsData {
	
	private int kills; //Stats for this game !
	private double damages;
	private double coinsWon;
	
	public StatsData(int kills, double damages, double coinsWon) {
		this.kills = kills;
		this.damages = damages;
		this.coinsWon = coinsWon;
	}
	
	public double getCoinsWon() {
		return coinsWon;
	}
	
	public double getDamages() {
		return damages;
	}
	
	public int getKills() {
		return kills;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public void setDamages(double damages) {
		this.damages = damages;
	}
	
	public void setCoinsWon(double coinsWon) {
		this.coinsWon = coinsWon;
	}

}
