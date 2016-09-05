package ru.MrBrikster.StopBotsJoin.connecters;

import org.bukkit.entity.Player;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResultListener;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;

public class LilyPadConnecter {
	
	public static void sendServer(final Player player, final String server) {
		try {
            Connect c = ConnectManager.getConnect();
            c.request(new RedirectRequest(server, player.getName())).registerListener(new FutureResultListener<RedirectResult>() {
            	@Override
            	public void onResult(RedirectResult redirectResult) {
                    if (redirectResult.getStatusCode() == StatusCode.SUCCESS) {
                        return;
                    }
                    player.kickPlayer("§cОшибка подключения к серверу.");
                }
            });
        } catch (Exception exception) {
            player.kickPlayer("§cОшибка подключения к серверу.");
        }
	}
}
