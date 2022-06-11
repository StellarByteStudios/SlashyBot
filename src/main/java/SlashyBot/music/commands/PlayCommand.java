package SlashyBot.music.commands;

import SlashyBot.Slashy;
import SlashyBot.commandManaging.commands.CommandStringConstants;
import SlashyBot.commandManaging.commands.ServerCommand;
import SlashyBot.music.AudioLoadResult;
import SlashyBot.music.MusicController;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand {
    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        // Den Command auseinandernehmen
        String[] commandStringArr = message.getContentDisplay().split(" ");

        // genug Argumente?
        if (commandStringArr.length > 1){
            // Checken, ist Auftragsteller überhaupt in einen Voice State hat
            GuildVoiceState state;
            if ((state = member.getVoiceState()) == null) {
                // Fehlernachricht zurück geben
                // Wenn nicht, einfach befehl abbrechen
                return;
            }

            // Checken, ist Auftragsteller überhaupt in einem Voice Channel
//            VoiceChannel voiceChannel;
            AudioChannel audioChannel;
            if ((audioChannel = state.getChannel()) == null){
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Du kannst sowas nur machen, wenn du in einem Voicechannel bist");
                builder.setColor(Color.RED);
                channel.sendMessage("Error").setEmbeds(builder.build()).queue();
                return;
            }
            // In welchem Channel ist er (Nachjoinen)
            // Player setuppen
            MusicController controller = Slashy.INSTANCE.playerManager.getController(audioChannel.getGuild().getIdLong());
            AudioPlayerManager audioPlayerManager = Slashy.INSTANCE.audioPlayerManager;
            AudioManager audioManager = audioChannel.getGuild().getAudioManager();

            // Dem VoiceChannel joinen falls nicht conneted
            if (!audioManager.isConnected()){
                System.out.println("Es gab noch keine Verbindung");
                audioManager.openAudioConnection(audioChannel);
            }

            // Übergebene URL zusammensetzten
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < commandStringArr.length; i++) {
                stringBuilder.append(commandStringArr[i])
                    .append(" ");
            }

            // Wenn es nur Schlagworte sind, Youtubesuche
            String url = stringBuilder.toString().trim();
            if (!url.startsWith("http")) {
                url = "ytsearch:" + url;
            }

            // Track laden
            audioPlayerManager.loadItem(url, new AudioLoadResult(url, controller));

            // Dem Controller sagen, dass er was spielt
            controller.setIsPlaying();
            return;
        }

        // Nicht genug
        // Fehlernachricht zurück geben
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Bitte benutze "
            + CommandStringConstants.PREFIX
            + CommandStringConstants.PLAY
            + " <url>");
        builder.setColor(Color.RED);
        channel.sendMessage("Error").setEmbeds(builder.build()).queue();
    }
}
