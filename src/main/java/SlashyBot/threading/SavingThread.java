package SlashyBot.threading;

import SlashyBot.Slashy;

public class SavingThread extends ThreadModel{
    // Wie oft wird zwischengespeichert
    private static final int INTERVALLTIME = 5000;
    public Slashy slashy;


    public SavingThread(Slashy slashy){
        super("Saving-Thread",
                "Constantly saves the Data of the Bot");
        this.slashy = slashy;
    }

    @Override
    protected void loop() {
        slashy.saveData();
        System.out.println("saving committed");

        try {
            Thread.sleep(INTERVALLTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
