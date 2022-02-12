package SlashyBot.commandManaging.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PatPatCommand implements ServerCommand{
    private int patpatamount = 0;

    public PatPatCommand(Integer pats) {
        this.patpatamount = pats;
    }

    public int getPatpatamount() {
        return patpatamount;
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        patpatamount++;
        String patmessage = "";
        patmessage +=  "Vielen Dank " + member.getEffectiveName() + ". Du hast Slashy Headpats gegeben :blush:";
        patmessage += "\nSlashy wurde nun schon " + patpatamount + " mal gepatted :hugging:";
        channel.sendMessage(patmessage).queue();
    }
}
