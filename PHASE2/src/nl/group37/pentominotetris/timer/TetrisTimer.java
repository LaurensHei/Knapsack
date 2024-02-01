package timer;

import main.TetrisGame;

public class TetrisTimer {

    private int speedMilliseconds;
    private double decreaseFactor;

    public boolean running;


    public TetrisTimer(int speedMilliseconds, double decreaseFactor) {
        this.speedMilliseconds = speedMilliseconds;
        this.decreaseFactor = decreaseFactor;
        this.running = false;
    }

    public void start()
    {
        this.running = true;
         new Thread(() -> {
            run();
        }).start();
    }

    private void run() {
        try {
            //pentomino 1 down
            if(!running)
                return;
            TetrisGame.board.removePentomino();
            TetrisGame.board.currentPentomino.moveDown();
            TetrisGame.board.addPentomino();
            TetrisGame.ui.setState(TetrisGame.board.getField());

            Thread.sleep(speedMilliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(running)
            run();

    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void increaseSpeed(int i) {
        for(int j = 0; j < i; j++) {
            this.speedMilliseconds = (int) Math.round(this.speedMilliseconds * this.decreaseFactor);

        }
    }


}
