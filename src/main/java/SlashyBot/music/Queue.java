package SlashyBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Queue {

    private List<AudioTrack> queueList;
    private MusicController controller;


    public Queue(MusicController controller) {
        this.controller = controller;
        this.queueList = new ArrayList<AudioTrack>();
    }

    // Geht in der Queue eins weiter
    public boolean next() {
        // Debugging
        System.out.println("Next Track will be played. Queue size = " + this.queueList.size());


        // Gucken ob die Queue leer ist
        if (this.queueList.size() < 1) {
            controller.playingStopped();
            return false;
        }

        // Die Queue ist nicht leer
        // Den neuen track laden und aus der Liste löschen
        AudioTrack track = queueList.remove(0);

        // wenn der Track nicht leer ist, wird er abgespielt
        if (track != null) {
            this.controller.getPlayer().playTrack(track);
            return true;
        }

        // Sonstige Fehler
        return false;
    }


    // Fügt der Queue einen weiteren Track hinzu
    public void enqueue(AudioTrack track) {
        // Track der Queue hinzufügen
        this.queueList.add(track);

        // Debugging
        System.out.println("Es wurde enqueued. Die Queue ist jetzt " + this.queueList.size() + " lang");

        // Wenn noch nichts abspielt, Player starten
        if (controller.getPlayer().getPlayingTrack() == null){
            controller.setIsPlaying();
            next();
        }
    }

    // Löscht alle Einträge in der Queue
    public void clearQueue() {
        this.queueList.clear();
    }

    // Würfelt die Liste durch
    public void shuffle() {
        Collections.shuffle(queueList);
    }



    public List<AudioTrack> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<AudioTrack> queueList) {
        this.queueList = queueList;
    }

    public MusicController getController() {
        return controller;
    }

    public void setController(MusicController controller) {
        this.controller = controller;
    }


}
