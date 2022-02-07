package commandManaging.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface ServerCommands {
    public void performCommand(Member member, TextChannel channel, Message message);

}
