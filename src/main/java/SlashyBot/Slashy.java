package SlashyBot;

import SlashyBot.commandManaging.CommandManager;
import SlashyBot.commandManaging.listener.CommandListener;
import SlashyBot.threading.SavingThread;
import SlashyBot.threading.ShutdownlistenerThread;
import SlashyBot.threading.ThreadModel;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

import java.util.HashSet;
import java.util.Set;

import static net.dv8tion.jda.api.entities.Activity.listening;
import static SlashyBot.secret.Tokenholder.BOT_TOKEN;

public class Slashy {

    // Selbstreferenz, damit die anderen Programmteile auch auf die Botinstanz zugreifen können
    public static Slashy INSTANCE;

    // Connection zum Bot
    public ShardManager shardManager;
    // CommandManager command -> magic -> supatolle Antwort
    private CommandManager commandManager;
    // Sammlung aller Threads die Laufen
    Set<ThreadModel> threads;

    public static void main(String[] args) {
        // Wir müssen am Anfang den Bot instanziieren
        try {
            new Slashy();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    // Konstruktor der eine Instanz des Bots erzeugt
    public Slashy() throws LoginException {
        // Erzeuge die Selbstreferenz
        INSTANCE = this;

        this.commandManager = new CommandManager();
        System.out.println("CommandManager gestartet");
        this.shardManager = configureShardManager();
        System.out.println("Bot geht online");
        startup();
        System.out.println("Bot wurde eingerichtet");
    }


    // Erstellt den Bot und übergibt ihm das wichtigste was er brauch
    private ShardManager configureShardManager() throws LoginException {
        // * * * Startup (Konfiguriert den Bot ganz nackig) * * * //
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("");
        builder.setToken(BOT_TOKEN);

        // * * * Einstellungen * * * //
        // Was macht er gerate
        builder.setActivity(listening("Male Hentai ASMR"));
        // Onlinestatus
        builder.setStatus(OnlineStatus.ONLINE);

        // Eventlistener hinzufügen
        builder.addEventListeners(new CommandListener());

        // Build (Starten des Bots)
        return builder.build();
    }


    // Richtet den Bot weiter ein (starten der Threads)
    private void startup(){
        createAndSaveThreads();
        startAllThreads();
    }

    private void startAllThreads() {
        for (ThreadModel thread : threads) {
            thread.start();
        }
    }

    public void endAllThreads() {
        for (ThreadModel thread : threads) {
            thread.endThread();
        }
    }

    private void createAndSaveThreads() {
        // Set erzeugen
        Set<ThreadModel> createdThreads = new HashSet<>();
        // Set befüllen
        createdThreads.add(new ShutdownlistenerThread(INSTANCE));
        createdThreads.add(new SavingThread(INSTANCE));
        // Set zurückgeben
        this.threads = createdThreads;
    }

    // Speichert die Daten im CommandManager persistent ab
    public void saveData(){
        this.commandManager.saveData();
    }

    // gibt den Zugriff auf den CommandManager frei
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
