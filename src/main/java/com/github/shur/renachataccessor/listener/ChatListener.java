package com.github.shur.renachataccessor.listener;

import com.github.shur.renachataccessor.RenaChatAccessor;
import com.github.shur.renachataccessor.chataccessor.ChatAccessorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final ChatAccessorManager chatAccessorManager = RenaChatAccessor.getChatAccessorManager();
        if (!chatAccessorManager.has(player)) return;

        chatAccessorManager.response(player, event.getMessage());

        event.setCancelled(true);
    }

}
