package com.github.shur.renachataccessor.listener;

import com.github.shur.renachataccessor.RenaChatAccessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class PluginListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(final PluginDisableEvent event) {
        if (!RenaChatAccessor.getPlugin().getName().equals(event.getPlugin().getName())) return;

        RenaChatAccessor.getChatAccessorManager().cancelAll();
    }
    
}
