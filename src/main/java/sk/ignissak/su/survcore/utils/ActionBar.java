package sk.ignissak.su.survcore.utils;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;


public class ActionBar {

    public void sendActionBar(Player p, String message) {
        try {
            Constructor<?> constructor = NMSManager.getInstance().getNMSClass("PacketPlayOutChat").getConstructor(
                    NMSManager.getInstance().getNMSClass("IChatBaseComponent"),
                    NMSManager.getInstance().getNMSClass("ChatMessageType"));

            Object icbc = NMSManager.getInstance().getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
            Object packet = constructor.newInstance(icbc,
                    NMSManager.getInstance().getNMSClass("ChatMessageType").getEnumConstants()[2]);
            Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

            playerConnection.getClass().getMethod("sendPacket", NMSManager.getInstance().getNMSClass("Packet"))
                    .invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
