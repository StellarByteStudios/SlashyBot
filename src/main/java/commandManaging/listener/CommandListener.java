package commandManaging.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static commandManaging.commands.CommandStringConstants.PREFIX;

public class CommandListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String message = event.getMessage().getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)){
            TextChannel channel = event.getTextChannel();

            if (message.startsWith(PREFIX)){

            }
        }
    }
}
