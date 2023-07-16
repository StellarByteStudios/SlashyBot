package SlashyBot.commandManaging.commands;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiceCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        // Speichern des Commands arr[0] und seiner Argumente
        String[] fullCommandArray = message.getContentDisplay().split(" ");

        String[] diceArguments = fullCommandArray[1].split("d");

        try{
            int diceValue = throwDice(Integer.parseInt(diceArguments[0]), Integer.parseInt(diceArguments[1]));
            channel.sendMessage("Das Ergebnis von einem "
                + diceArguments[1] + ", " + diceArguments[0] + " mal gewuerfelt ist:  " + diceValue).queue();
        } catch (Exception e){
            channel.sendMessage("Da hast du wohl was falsch gemacht...").queue();
            e.printStackTrace();
        }

    }

    private int throwDice(int amount, int dice){
        int summe = 0;
        Random random = new Random();

        for (int i = 0; i < amount; i++){
            summe += ThreadLocalRandom.current().nextInt(1, dice +1);
        }

        return summe;
    }
}
