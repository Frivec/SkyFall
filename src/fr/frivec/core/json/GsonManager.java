package fr.frivec.core.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonManager {
	
	private Gson gson;
	
	private BufferedReader bufferedReader;
	private StringBuilder builder;
	private String line;
	
	public GsonManager() {
		this.setGson(createGsonInstance());
	}
	 
	private Gson createGsonInstance() {
		return new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.disableHtmlEscaping()
				.create();
	}
	
	public String getInfo(UUID uuid, String info) {
		
		String stringURL = "https://stats.epicube.fr/player/" + uuid.toString() + ".json";
		String rank = null;
		
		System.out.println("slt: " + stringURL);
		
		try {
			
			URL url = new URL(stringURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			connection.connect();
			
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			builder = new StringBuilder();
			
			while((line = bufferedReader.readLine()) != null) {
				builder.append(line + "\n");
			}
			
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(builder.toString());
			
			JsonElement rankElement = jsonObject.get(info);
			
			if(rankElement != null)
				rank = rankElement.toString();
			
			connection.disconnect();
			
			if(rank.contains("<i class='fa fa-star-o'></i>"))
				rank = rank.replace("<i class='fa fa-star-o'></i>", "✪");
			
			return rank;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rank;
		
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

}
