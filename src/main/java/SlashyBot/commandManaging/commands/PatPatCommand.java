package SlashyBot.commandManaging.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PatPatCommand implements ServerCommand{
    private static int patpatamount = 0;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        patpatamount++;
        String patmessage = "";
        patmessage +=  "Vielen Dank " + member.getEffectiveName() + ". Du hast Slashy Headpats gegeben :)";
        patmessage += "\nSlashy wurde nun schon " + patpatamount + " mal gepatted :)";
        channel.sendMessage(patmessage).queue();
    }
}
