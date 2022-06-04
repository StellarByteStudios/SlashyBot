package SlashyBot.music;

import SlashyBot.Slashy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PlayerManager {

    // Map in der alle Instanzen von Controllern mit ihren eigenen Player gespeichert werden
    public ConcurrentMap<Long, MusicController> controller;

    public PlayerManager() {
        this.controller = new ConcurrentHashMap<Long, MusicController>();
    }

    public MusicController getController(long guildId){
        // Rückgaber erstmal erzeugen
        MusicController mc = null;

        // Prüfen ob die ID existiert
        if (this.controller.containsKey(guildId)){
            // Wenn ja, wieder zurückgeben
            mc = this.controller.get(guildId);
            return mc;
        }
        // Wenn nein, neuen erstellen
        mc = new MusicController(Slashy.INSTANCE.shardManager.getGuildById(guildId));
        // Abspeichern
        this.controller.put(guildId, mc);
        // und zurück geben
        return mc;
    }
}
