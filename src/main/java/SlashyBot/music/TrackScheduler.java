package SlashyBot.music;

import SlashyBot.Slashy;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

public class TrackScheduler extends AudioEventAdapter {

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // Debugging
        System.out.println("Track started. Name: " + track.getInfo());

        // Id der Guild holen
        long guildid = Slashy.INSTANCE.playerManager.getGuildByPlayerHash(player.hashCode());
        // Guild mit dieser Id holen
        Guild guild = Slashy.INSTANCE.shardManager.getGuildById(guildid);

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Debugging
        System.out.println("Track ended");
        
        // Id der Guild holen
        long guildid = Slashy.INSTANCE.playerManager.getGuildByPlayerHash(player.hashCode());
        // Guild mit dieser Id holen
        Guild guild = Slashy.INSTANCE.shardManager.getGuildById(guildid);

        MusicController controller = Slashy.INSTANCE.playerManager.getController(guildid);
        Queue queue = controller.getTrackqueue();
//        if (endReason.mayStartNext) { // wurde im Video gezeigt, funzt aber nicht mit skip
        if (queue.getQueueList().size() != 0) {
            

            // Debugging
            System.out.println("Ein Track wurde gerade beendet und der nächste gestartet");

            // Ist noch etwas in der Queue, dann abspielen
            if (queue.next()) {
                return;
            }
        }

        // Es ist nichts mehr in der Queue, player beenden
        AudioManager manager = guild.getAudioManager();
        player.stopTrack();
        manager.closeAudioConnection();
    }
}
