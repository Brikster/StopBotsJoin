package ru.MrBrikster.StopBotsJoin.connecters;

import lilypad.client.connect.api.Connect;
import ru.MrBrikster.StopBotsJoin.Main;

public class ConnectManager {
	
	public static Connect getConnect() {
		return Main.getPlugin().getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}

}
