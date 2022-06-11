package SlashyBot.music.commands;

import SlashyBot.Slashy;
import SlashyBot.commandManaging.commands.ServerCommand;
import SlashyBot.music.AudioLoadResult;
import SlashyBot.music.MusicController;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        // Checken, ist Auftragsteller überhaupt in einen Voice State hat
        GuildVoiceState state;
        if ((state = member.getVoiceState()) == null) {
            // Fehlernachricht zurück geben
            // Wenn nicht, einfach befehl abbrechen
            return;
        }

        // Checken, ist Auftragsteller überhaupt in einem Voice Channel
        AudioChannel audioChannel;
        if ((audioChannel = state.getChannel()) == null){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Du kannst sowas nur machen, wenn du in einem Voicechannel bist");
            builder.setColor(Color.RED);
            channel.sendMessage("Error").setEmbeds(builder.build()).queue();
            return;
        }
        // Player holen zum stoppen
        MusicController controller = Slashy.INSTANCE.playerManager.getController(audioChannel.getGuild().getIdLong());
        AudioManager audioManager = audioChannel.getGuild().getAudioManager();
        AudioPlayer player = controller.getPlayer();


        // Debugging
        System.out.println("Der Player wird gstoppt und die Queue geleert");
        
        // Audio anhalten
        player.stopTrack();
        // Connection beenden
        audioManager.closeAudioConnection();

        // Liste leeren
        controller.clearQueue();

        // Abschiedsnachricht
        message.addReaction("U+1F44C").queue();
    }
}
