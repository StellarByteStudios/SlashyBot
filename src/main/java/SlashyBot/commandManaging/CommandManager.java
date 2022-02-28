package SlashyBot.commandManaging;

import SlashyBot.commandManaging.commands.CultureCommand;
import SlashyBot.commandManaging.commands.DeleteCommand;
import SlashyBot.commandManaging.commands.PatPatCommand;
import SlashyBot.commandManaging.commands.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static SlashyBot.commandManaging.commands.CommandStringConstants.*;
import static SlashyBot.saving.PathHolderClass.COUNTER_FILE;

public class CommandManager {

    // Hier werden alle Commands gespeichert
    public ConcurrentHashMap<String, ServerCommand> commands;
    // Speicher für die Counter
    private HashMap<String, Integer> counter;

    // Erzeugt die HashMap und läd die Comments rein
    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();
        // Liest die ganzen Counterstände aus nem File und speichert diese in der Map
        this.counter = loadCounterMap();
        loadCommands();
    }

    private void loadCommands(){
        commands.put(DELETE, new DeleteCommand());
        commands.put(PATPAT, new PatPatCommand(counter.get("pats")));
        commands.put(CULTURE, new CultureCommand());
    }

    private HashMap<String, Integer> loadCounterMap(){
        // Map erzeugen
        HashMap<String, Integer> countmap = new HashMap<>();

        try {
            // Streams erzeugen
            FileInputStream fileInput = new FileInputStream(new File(COUNTER_FILE));
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            // Map aus dem Savefile Holen
            countmap = (HashMap<String, Integer>) objectInput.readObject();

            // Schließen der Streams
            objectInput.close();
            fileInput.close();

        } catch (IOException | ClassNotFoundException ioException) {
            System.out.println("Noch kein Savefile vorhanden. Wird beim naesten speichern erstellt");
            countmap.put("pats" , 0);
        }

        return countmap;
    }


    public boolean perform(String commandName, Member member, TextChannel channel, Message message){

        // Schaut nach, ob es den Kommand gibt und sucht ihn dann raus
        ServerCommand command = commands.get(commandName.toLowerCase());

        // Überprüft, obs den Command überhaupt gab
        if (command == null){
            channel.sendMessage("Lern schreiben " + member.getEffectiveName() + ". Den Command gibts garnicht!").queue();
            //channel.sendMessage("https://tenor.com/view/eat-shit-screw-you-shut-up-fuck-you-upset-gif-14472028").queue();
            return false;
        }
        // Command wird ausgeführt
        command.performCommand(member, channel, message);
        return true;
    }

    // Diese Methode soll im Falle eines Shutdowns alles abspeicher
    public void saveData(){
        try {
            // Outputstream für das SaveFile erzeugen
            FileOutputStream fileOutputStream = new FileOutputStream(new File(COUNTER_FILE));
            // Objectstream mit diesem Filestream erzeugen
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Updaten der Map
            counter = collectCounterData();

            // Abspeichern der Map
            objectOutputStream.writeObject(this.counter);

            // Schließen der Streams
            objectOutputStream.close();
            fileOutputStream.close();


        // Fangen der Exceptions
        } catch (FileNotFoundException exception){
            // Das File existiert noch nicht, also muss es erzeugt werden
            // Baue erst den Pfad zusammen
//            counterfile = new File("");
//            String pathAbs = counterfile.getAbsolutePath();
//            String newPath = pathAbs + "\\" + COUNTER_FILE;
            String newPath = new File("").getAbsolutePath() + "\\" + COUNTER_FILE;
            // Erzeuge alle Ordner. Wenn diese Vorhanden sind,
            // wird beim nächsten speichern die Datei automatisch erstellt
            new File(newPath).getParentFile().mkdirs();
            // Mitteilung auf der Konsole
            System.out.println("Ordnerstruktur zum abspeichern erstellt");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private HashMap<String, Integer> collectCounterData() {
        HashMap<String,Integer> updatedData = new HashMap<>();

//        ServerCommand serverCommand = commands.get(PATPAT);
//        PatPatCommand patPatCommand = (PatPatCommand) serverCommand;
//        int amount = patPatCommand.getPatpatamount();
//        updatedData.put(PATPAT,amount);
        // PatpatData holen
        updatedData.put("pats",((PatPatCommand) (commands.get(PATPAT))).getPatpatamount());

        return updatedData;
    }
}
