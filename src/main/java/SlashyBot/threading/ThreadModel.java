package SlashyBot.threading;

// Eine Vorlage für alle Treads die ich machen will. Lässt das handling wie
// starten, stoppen und der gleichen leichert mahcen
public abstract class ThreadModel extends Thread{

    // Solange running true ist, läuft die while in
    // der run()-Methode durch
    private boolean running;

    private final String name;
    private final String description;

    public ThreadModel(){
        this.name = "Not Named";
        this.description = "No description provided";
    }

    public ThreadModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public final void run(){
        this.running = true;
        startMessage();
        startup();
        while (running){
            loop();
        }
        cleanup();
        endMessage();
        return;
    }

    // Leeres Startup und Cleanup, falls nicht benötigt
    protected void startup() { return; }
    protected void cleanup() { return; }

    // Der Loop, der vom Thread implementiert werden muss
    protected abstract void loop();

    private void startMessage(){
        System.out.println("The Thread: \"" + name + "\" was started");
    }

    private void endMessage(){
        System.out.println("The Thread: \"" + name + "\" was ended");
    }

    public void endThread(){
        this.running = false;
    }

    @Override
    public String toString() {
        String out =  "The Thread \"" + name +
                "\" is described as: " + description;
        if (running) {
            return out + ". It is currently running";
        }
        return out + ". It doesn´t run at the moment";
    }
}
