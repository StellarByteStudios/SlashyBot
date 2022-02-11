package SlashyBot.commandManaging.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CultureCommand implements ServerCommand{

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        channel.sendMessage("https://tenor.com/view/culture-man-of-culture-cultured-gif-10903367").queue();
    }
}
