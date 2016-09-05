package ru.MrBrikster.StopBotsJoin.connecters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import ru.MrBrikster.StopBotsJoin.Main;

public class BungeeConnecter {
	
	public static void sendServer(final Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable(){

			@Override
			public void run() {
				if (player.isOnline()) {
					player.kickPlayer("§cОшибка подключения к серверу.");
				}
			}
			
		}, 60L);
	}

}
