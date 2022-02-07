package SlashyBot.commandManaging;

import SlashyBot.commandManaging.commands.DeleteCommand;
import SlashyBot.commandManaging.commands.PatPatCommand;
import SlashyBot.commandManaging.commands.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;
import static SlashyBot.commandManaging.commands.CommandStringConstants.DELETE;
import static SlashyBot.commandManaging.commands.CommandStringConstants.PATPAT;

public class CommandManager {

    // Hier werden alle Commands gespeichert
    public ConcurrentHashMap<String, ServerCommand> commands;

    // Erzeugt die HashMap und läd die Comments rein
    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();
        loadCommands();
    }

    private void loadCommands(){
        commands.put(DELETE, new DeleteCommand());
        commands.put(PATPAT, new PatPatCommand());
    }


    public boolean perform(String commandName, Member member, TextChannel channel, Message message){

        // Schaut nach, ob es den Kommand gibt und sucht ihn dann raus
        ServerCommand command = commands.get(commandName.toLowerCase());

        // Überprüft, obs den Command überhaupt gab
        if (command == null){
            channel.sendMessage("Lern schreiben " + member.getEffectiveName() + ". Den Command gibts garnicht!").queue();
            return false;
        }
        // Command wird ausgeführt
        command.performCommand(member, channel, message);
        return true;
    }
}
