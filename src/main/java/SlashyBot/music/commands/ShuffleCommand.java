package SlashyBot.music.commands;

import SlashyBot.Slashy;
import SlashyBot.commandManaging.commands.ServerCommand;
import SlashyBot.music.MusicController;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class ShuffleCommand implements ServerCommand {
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
        // Queue des Controllers holen und durchmischeln
        MusicController controller = Slashy.INSTANCE.playerManager.getController(audioChannel.getGuild().getIdLong());
        
        //Debugging
        System.out.println("Queue wurde durchgemischt");
        
        // Liste Mischen
        controller.getTrackqueue().shuffle();

        // Bestätigungsnachricht
        message.addReaction("U+1F44D").queue();
    }
}
