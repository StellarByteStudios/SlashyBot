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

    @Override
    public void trackLoaded(AudioTrack track) {
        controller.getPlayer().playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }
}
