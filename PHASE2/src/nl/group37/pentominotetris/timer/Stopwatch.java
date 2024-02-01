package timer;

import main.TetrisGame;

public class Stopwatch {


    public boolean running;
    public String render;
    public int rMillis;
    public int rSeconds;
    public int rMinutes;


    public Stopwatch() {
        this.render = "00 : 00 : 0000";
    }

    public void start() {
        this.running = true;
        new Thread(() -> {
            run();
        }).start();
    }


    private void run() {
        while(running) {
            try {
                Thread.sleep(1);
                update();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void stop() {
        this.running = false;
    }
    public boolean isRunning() {
        return this.running;
    }

    public void update() {
        this.rMillis++;

        if(rMillis == 1000) {
            rMillis = 0;
            rSeconds++;
        }
        if(rSeconds == 60) {
            rSeconds = 0;
            rMinutes++;
        }
        this.render = String.format("%02d", rMinutes) + " : " + String.format("%02d", rSeconds)  + " : " + String.format("%04d", rMillis);


        TetrisGame.ui.setTime(this.render);
    }


}
