package SlashyBot.commandManaging.listener;

import SlashyBot.Slashy;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static SlashyBot.commandManaging.commands.CommandStringConstants.PREFIX;

public class CommandListener extends ListenerAdapter {


    // Wenn ein eine beliebige Nachricht empfangen wird
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        // Abbruch, wenn es nicht in einem Einfachen Textchannel geschrieben wurde
        if (!event.isFromType(ChannelType.TEXT)){
            return;
        }

        // Lese den Inhalt der Nachricht als String ein
        String messageContentAsString = event.getMessage().getContentDisplay();

        // wenn die Nachricht nicht mit dem Prefix anfängt -> wegwerfen
        if (!messageContentAsString.startsWith(PREFIX)){
            return;
        }

        // Hole mir den Command + seine Argumente. Wird an Leerzeichen auseinander gezogen
        // Außerdem wird der Präfix weggelöscht
        // Der Kommand steht an fullCommandMessage[0] die Argumente dahinter
        String[] fullCommandMessage = messageContentAsString.substring(PREFIX.length()).split(" ");

        // Wurde hinter dem Präfix nix mehr geschrieben
        if (fullCommandMessage.length < 1){
            // To-Do: Noch ne Fehlermeldung
            return;
        }

        // Hole mir die Daten für den Perform
        TextChannel channel = event.getTextChannel();   // Channel in dem die Nachricht geschrieben wurde
        String commandName = fullCommandMessage[0];     // Command der ausgeführt werden soll
        Member member = event.getMember();              // Autor des Commands
        Message message = event.getMessage();           // Inhalt der Nachricht

        // Los gehts mit dem verarbeiten der Commands
        Slashy.INSTANCE.getCommandManager().perform(commandName, member, channel, message);
    }
}
