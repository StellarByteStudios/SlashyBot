package commandManaging;

import commandManaging.commands.ClearCommand;
import commandManaging.commands.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

import static commandManaging.commands.CommandStringConstants.CLEAR;

public class CommandManager {

    // Hier werden alle Commands gespeichert
    public ConcurrentHashMap<String, ServerCommand> commands;

    // Erzeugt die HashMap und läd die Comments rein
    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();
        loadCommands();
    }

    private void loadCommands(){
        commands.put(CLEAR, new ClearCommand());
    }


    public boolean perform(String commandName, Member member, TextChannel channel, Message message){

        ServerCommand command = commands.get(commandName.toLowerCase());
        if (command == null){
            return false;
        }
        command.performCommand(member, channel, message);
        return true;
    }
}
