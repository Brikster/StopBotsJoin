package ru.MrBrikster.StopBotsJoin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.entity.Player;

public class IPLogger {
	
	private FileWriter fw;
	private PrintWriter pw;

	public IPLogger() {
		File dataFolder = Main.getPlugin().getDataFolder();
		if(!dataFolder.exists()) {
		    dataFolder.mkdir();
		}
		File file = new File(Main.getPlugin().getDataFolder(), "iplist.txt");
    	if (!file.exists()) {
    		try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	try {
			fw = new FileWriter(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	pw = new PrintWriter(fw);
	}
	
	public void logIP(Player player) {        		
		pw.print(player.getAddress().getAddress().toString().substring(1, player.getAddress().getAddress().toString().length()).split(":")[0] + '\n');
		pw.flush();
    }
	
}
