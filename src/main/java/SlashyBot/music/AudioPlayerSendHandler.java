package SlashyBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import java.nio.ByteBuffer;
import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler{

    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    // Konstructor für den Handler
    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    // Fragt ab, ob ein "Job zu tun ist"
    @Override
    public boolean canProvide() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    // holt die Daten des lastFrames als ByteBuffer
    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    // Lavaplayer unterstützt nur Opus
    @Override
    public boolean isOpus() {
        return true;
    }
}
