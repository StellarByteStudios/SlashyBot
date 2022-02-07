package SlashyBot.commandManaging.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

// Löscht die x zuletzt geschriebene Nachricht (Plus den Command selber)
public class DeleteCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        // Speichern des Commands arr[0] und seiner Argumente
        String[] fullCommandArray = message.getContentDisplay().split(" ");

        // Hat der Befehlende überhaupt die Berechtigung dazu?
        if (!member.hasPermission(channel, Permission.MESSAGE_MANAGE)){
            channel.sendMessage("Du hast hier ganix zu sagen!").queue();
            return;
        }
        // Wurde die richtige Anzahl übergeben?
        if (fullCommandArray.length != 2){
            channel.sendMessage("Du weist schon das du mir richtig sagen musst, wie viel du löschen willst?").queue();
            return;
        }
        // Ist es auch eine Zahl?
        int amount = 0;
        try{
            amount = Integer.parseInt(fullCommandArray[1]) + 1; // + 1, weil der Command ja auch noch ne Nachicht ist
        } catch (NumberFormatException e){
            channel.sendMessage("Du musst auch schon Zahlen benutzen?").queue();
            return;
        }
        //Sucht die Nachrichten raus und löscht sie
        channel.purgeMessages(getLastMessagesWithoutPinns(channel, amount));
        channel.sendMessage("Die letzten " + (amount-1) + " Nachrichten geloescht (ohne pins)").queue();

    }

    private List<Message> getLastMessagesWithoutPinns(MessageChannel channel, int amount){
        List<Message> messages = new ArrayList<>();

        int count = amount;
        for (Message message : channel.getIterableHistory().cache(false)) {
            // gepinnte Nachichten werden nicht mit einberechnet
            if (message.isPinned()){
                continue;
            }
            // Fügt die Nachricht der Liste hinzu
            messages.add(message);

            // Wenn wir die #amount# messages haben, die nicht angepinnt sind, brechen wir ab
            count--;
            if (count <= 0){
                break;
            }
        }
        return  messages;
    }
}
