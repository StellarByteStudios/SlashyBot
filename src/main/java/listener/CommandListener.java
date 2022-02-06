package listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static constants.CommandStrings.PREFIX;

public class CommandListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)){
            TextChannel channel = event.getTextChannel();

            if (message.startsWith(PREFIX)){
                // fullCommand[0] ist immer das Prefix
                String[] fullCommand = message.split(" ");
                String command = fullCommand[1];

                // Nimmt alle Kommands auf mit nur sich selbst als parameter
                if (fullCommand.length == 2){
                    // Erste einstellige Commands
                    if (fullCommand[1].equalsIgnoreCase("moin")){
                        // Nachricht senden mit Namennennung
                        channel.sendMessage("Seeervus, mooooin").queue();
                        channel.sendMessage("Was willst du von mir " + event.getMember().getEffectiveName() + "?").queue();
                    }
                    else if(fullCommand[1].equalsIgnoreCase("time")){
                        // Gibt die Uhrzeit aus und pingt den Verfasser
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
                        channel.sendMessage("Guck doch selba auf die Uhr " + event.getMember().getAsMention() + "!").queue();
                        channel.sendMessage("Aber weils dus bist " + date.format(calendar.getTime())).queue();
                    }
                }
                // Notizen für später
//            } else{
//                if (event.getAuthor().isBot()){
//                    System.out.println("Der Bot soll sich nicht selber antworten");
//                } else {
//                    channel.sendMessage("Lern Deutsch du Hurasones!!").queue();
//                }
            }
        }
    }
}
