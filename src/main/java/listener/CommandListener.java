package listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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

                if (fullCommand.length == 2){
                    if (fullCommand[1].equalsIgnoreCase("moin")){
                        channel.sendMessage("Seeervus, mooooin").queue();
                        channel.sendMessage("Was willst du von mir " + event.getMember().getEffectiveName() + "?").queue();
                    }
                }
            }
        }
    }
}
