package com.relicum.scb.hooks;

import net.milkbowl.vault.chat.Chat;

/**
 * SuperSkyBros First Created 19/11/13
 *
 * @author Relicum
 * @version 0.1
 */
public class ChatManager {

    Chat chat;

    public ChatManager() {
        chat = getChat();

    }

    public static Chat getChat() {
        return VaultManager.getInstance().getChat();
    }
}
