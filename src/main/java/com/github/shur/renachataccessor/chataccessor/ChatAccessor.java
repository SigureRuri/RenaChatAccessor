package com.github.shur.renachataccessor.chataccessor;

import org.bukkit.entity.Player;


public final class ChatAccessor {

    /* package */ final Player player;

    /* package */ String id = null;

    /* package */ long expirationTicks = 0;

    /* package */ ResponseListener onResponse = (s) -> {};

    /* package */ CancelListener onCancel = () -> {};

    public ChatAccessor(Player player) {
        this.player = player;
    }

    public ChatAccessor id(String id) {
        this.id = id;
        return this;
    }

    public ChatAccessor expirationTicks(long ticks) {
        this.expirationTicks = ticks;
        return this;
    }

    public ChatAccessor onResponse(ResponseListener onResponse) {
        this.onResponse = onResponse;
        return this;
    }

    public ChatAccessor onCancel(CancelListener onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public interface ResponseListener {
        void run(String input);
    }

    public interface CancelListener {
        void run();
    }

}
