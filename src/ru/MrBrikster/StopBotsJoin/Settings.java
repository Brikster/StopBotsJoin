package ru.MrBrikster.StopBotsJoin;

import org.bukkit.World;

public class Settings {
	
	private static Settings settings;
	private World world;
	private int maxerrors;
	private boolean enablekick;
	private String message;
	private ProxyMode proxy;
	private String server;

	public Settings(World world, int maxerrors, boolean enablekick, String message, ProxyMode proxy, String server) {
		this.world = world;
		this.maxerrors = maxerrors;
		this.enablekick = enablekick;
		this.message = message.replace("&", "§");
		this.proxy = proxy;
		this.server = server;
	}
	
	public World getWorld() {
		return world;
	}
	
	public int getMaxErrors() {
		return maxerrors;
	}
	
	public boolean isKickEnabled() {
		return enablekick;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ProxyMode getProxyMode() {
		return proxy;
	}
	
	public String getServer() {
		return server;
	}
	
	public static void loadSettings(World world, int maxerrors, boolean enablekick, String message, ProxyMode proxy, String server) {
		settings = new Settings(world, maxerrors, enablekick, message, proxy, server);
	}
	
	public static Settings getSettings() {
		return settings;
	}

}
