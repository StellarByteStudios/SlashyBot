package SlashyBot.music;

import SlashyBot.Slashy;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;

// ist eine Instanz eines MusicControllers (Darf immer nur einen Player haben)
public class MusicController {

    private Guild guild;
    private AudioPlayer player;

    public MusicController(Guild guild){
        this.guild = guild;
        this.player = Slashy.INSTANCE.audioPlayerManager.createPlayer();

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
        // Lautstärke ist von Haus aus sehr laut deswegen leiser
        this.player.setVolume(10);
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }


}
