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
        // Überschreibt sofort die aktuelle wiedergabe
        controller.getPlayer().playTrack(track);
    }

    // Wenn eine Playlist geladen wird
    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        // To-Do
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
