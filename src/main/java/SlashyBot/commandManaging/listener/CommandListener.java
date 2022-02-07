package SlashyBot.commandManaging.listener;

import net.dv8tion.jda.api.entities.ChannelType;
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
        String message = event.getMessage().getContentDisplay();

        // wenn die Nachricht nicht mit dem Prefix anfängt -> wegwerfen
        if (!message.startsWith(PREFIX)){
            return;
        }

        // Hole mir den Channel in dem die Nachricht geschrieben wurde
        TextChannel channel = event.getTextChannel();

        // Hole mir den Command + seine Argumente. Wird an Leerzeichen auseinander gezogen
        // Außerdem wird der Präfix weggelöscht
        String[] fullCommandMessage = message.substring(PREFIX.length()).split(" ");

        // Wurde hinter dem Präfix nix mehr geschrieben
        if (fullCommandMessage.length < 1){
            // To-Do: Noch ne Fehlermeldung
            return;
        }

        // Los gehts mit dem verarbeiten der Commands
    }
}
