package SlashyBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class AudioLoadResult implements AudioLoadResultHandler {

    private final String uri;
    private final MusicController controller;


    public AudioLoadResult(String uri, MusicController controller) {
        this.uri = uri;
        this.controller = controller;
    }

    // Wenn ein einziger Track angefragt wird
    @Override
    public void trackLoaded(AudioTrack track) {
        // Überschreibt sofort die aktuelle Wiedergabe
        controller.getPlayer().playTrack(track);

        // Debugging
        System.out.println("Es wurde ein einzelnes Lied gequeued");
    }

    // Wenn eine Playlist geladen wird
    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        Queue queue = controller.getTrackqueue();

        // Darf nur direkter Link sein, keine Suche. Nimmt sonst einfach erstes
        if (uri.startsWith("ytsearch: ")) {
            queue.enqueue(playlist.getTracks().get(0));
            return;
        }

        int trackcount = 0;
        for (AudioTrack track : playlist.getTracks()) {
            queue.enqueue(track);
            trackcount++;
        }

        // Debugging
        System.out.println("Es wurden " + trackcount + " Lieder geladen");
    }

    // Wenn die Suche keine Ergebnisse liefert
    @Override
    public void noMatches() {
        // To-Do: Fehlermeldung
    }

    // Wenn das gesuchte nicht laden kann (Länderzugriffsverweigerung, etc.)
    @Override
    public void loadFailed(FriendlyException exception) {
        // To-Do: Fehlermeldung
    }
}
