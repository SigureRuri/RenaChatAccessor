package com.github.shur.renachataccessor.chataccessor;

import com.github.shur.renachataccessor.RenaChatAccessor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ChatAccessorManager {

    private final Map<UUID, ChatAccessor> accessors = new HashMap<>();

    public void register(final ChatAccessor chatAccessor) {
        final Player player = chatAccessor.player;
        final UUID uuid = player.getUniqueId();

        if (accessors.containsKey(uuid)) accessors.get(uuid).onCancel.run();

        accessors.put(uuid, chatAccessor);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (accessors.containsKey(uuid) && accessors.get(uuid) == chatAccessor) {
                    ChatAccessorManager.this.cancel(uuid);
                }
            }
        }.runTaskLater(RenaChatAccessor.getPlugin(), chatAccessor.expirationTicks);
    }

    public void response(final UUID uuid, final String input) {
        if (!accessors.containsKey(uuid)) return;

        final ChatAccessor accessor = accessors.get(uuid);
        accessor.onResponse.run(input);

        accessors.remove(uuid);
    }

    public void response(final Player player, final String input) {
        response(player.getUniqueId(), input);
    }

    public void cancel(final UUID uuid) {
        if (!accessors.containsKey(uuid)) return;

        final ChatAccessor accessor = accessors.get(uuid);
        accessor.onCancel.run();

        accessors.remove(uuid);
    }

    public void cancel(final Player player) {
        cancel(player.getUniqueId());
    }

    public void cancelAll() {
        accessors.keySet().forEach(this::cancel);
    }

    public boolean hasChatAccessor(UUID uuid) {
        return accessors.containsKey(uuid);
    }

    public boolean hasChatAccessor(Player player) {
        return hasChatAccessor(player.getUniqueId());
    }

}
