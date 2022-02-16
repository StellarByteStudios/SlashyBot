package SlashyBot;

import SlashyBot.commandManaging.CommandManager;
import SlashyBot.commandManaging.listener.CommandListener;
import SlashyBot.threading.SavingThread;
import SlashyBot.threading.ThreadModel;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static net.dv8tion.jda.api.OnlineStatus.OFFLINE;
import static net.dv8tion.jda.api.entities.Activity.listening;
import static SlashyBot.secret.Tokenholder.BOT_TOKEN;

public class Slashy {

    // Selbstreferenz, damit die anderen Programmteile auch auf die Botinstanz zugreifen können
    public static Slashy INSTANCE;

    // Connection zum Bot
    public ShardManager shardManager;
    // CommandManager command -> magic -> supatolle Antwort
    private CommandManager commandManager;


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
        System.out.println("Alle Konsolen Threads gestartet");
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


    // Startet Alle Threads, die die Konsole auslesen
    private void startup(){
    }


    }

    // Speichert die Daten im CommandManager persistent ab
    private void saveData(){
        this.commandManager.saveData();
    }

    // gibt den Zugriff auf den CommandManager frei
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
