package com.github.shur.renachataccessor;

import com.github.shur.renachataccessor.chataccessor.ChatAccessorManager;
import com.github.shur.renachataccessor.listener.ChatListener;
import com.github.shur.renachataccessor.listener.PluginListener;
import com.github.shur.renachataccessor.listener.QuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RenaChatAccessor extends JavaPlugin {

    private static boolean enabled = false;

    private static JavaPlugin plugin = null;

    private static final ChatAccessorManager chatAccessorManager = new ChatAccessorManager();

    public static void onEnable(JavaPlugin plugin) {
        if (enabled) return;
        enabled = true;
        RenaChatAccessor.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(new ChatListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new QuitListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PluginListener(), plugin);
    }

    public static boolean isPluginEnabled() {
        return enabled;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static ChatAccessorManager getChatAccessorManager() {
        return chatAccessorManager;
    }

    @Override
    public void onEnable() {
        onEnable(this);
    }

}
