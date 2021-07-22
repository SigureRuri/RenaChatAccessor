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
        if (chatAccessor == null) throw new IllegalArgumentException("chatAccessor must not be null");

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
        if (uuid == null) throw new IllegalArgumentException("uuid must not be null");
        if (input == null) throw  new IllegalArgumentException("input must not be null");

        if (!accessors.containsKey(uuid)) return;

        final ChatAccessor accessor = accessors.get(uuid);
        accessor.onResponse.run(input);

        accessors.remove(uuid);
    }

    public void response(final Player player, final String input) {
        if (player == null) throw new IllegalArgumentException("player must not be null");
        if (input == null) throw  new IllegalArgumentException("input must not be null");

        response(player.getUniqueId(), input);
    }

    public void cancel(final UUID uuid) {
        if (uuid == null) throw new IllegalArgumentException("player must not be null");

        if (!accessors.containsKey(uuid)) return;

        final ChatAccessor accessor = accessors.get(uuid);
        accessor.onCancel.run();

        accessors.remove(uuid);
    }

    public void cancel(final Player player) {
        if (player == null) throw new IllegalArgumentException("player must not be null");

        cancel(player.getUniqueId());
    }

    public void cancelAll() {
        accessors.keySet().forEach(this::cancel);
    }

    public boolean has(UUID uuid) {
        if (uuid == null) throw new IllegalArgumentException("player must not be null");

        return accessors.containsKey(uuid);
    }

    public boolean has(Player player) {
        if (player == null) throw new IllegalArgumentException("player must not be null");

        return has(player.getUniqueId());
    }

    public boolean has(UUID uuid, String id) {
        if (uuid == null) throw new IllegalArgumentException("uuid must not be null");
        if (id == null) throw new IllegalArgumentException("id must not be null");

        if (!accessors.containsKey(uuid)) return false;
        final String accessorsId = accessors.get(uuid).id;
        return accessorsId != null && accessorsId.equals(id);
    }

}
