package controls;

import java.awt.KeyEventPostProcessor;
import java.awt.event.KeyEvent;

import main.TetrisGame;

public class Controls implements KeyEventPostProcessor {

    private static final int moveRightKey = KeyEvent.VK_RIGHT;
    private static final int moveLeftKey = KeyEvent.VK_LEFT;
    private static final int rotateRight = KeyEvent.VK_UP;
    private static final int rotateLeft = KeyEvent.VK_Z;
    private static final int hardDrop = KeyEvent.VK_SPACE;
    private static final int softDrop = KeyEvent.VK_DOWN;
    private static final int pause = KeyEvent.VK_ESCAPE;
    


    @Override
    public boolean postProcessKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case moveRightKey:
                    if(!TetrisGame.timer.isRunning() || TetrisGame.isOptimalOrderActive() || TetrisGame.isBotActive())
                        break;
                    TetrisGame.board.removePentomino();
                    TetrisGame.board.currentPentomino.moveRight();
                    TetrisGame.board.addPentomino();
                    TetrisGame.ui.setState(TetrisGame.board.getField());
                    break;
                case moveLeftKey:
                    if(!TetrisGame.timer.isRunning() || TetrisGame.isOptimalOrderActive() || TetrisGame.isBotActive())
                        break;
                    TetrisGame.board.removePentomino();
                    TetrisGame.board.currentPentomino.moveLeft();
                    TetrisGame.board.addPentomino();
                    TetrisGame.ui.setState(TetrisGame.board.getField());
                    break;
                case softDrop:
                    if(!TetrisGame.timer.isRunning())
                        break;
                    TetrisGame.board.removePentomino();
                    TetrisGame.board.currentPentomino.moveDown();
                    TetrisGame.board.addPentomino();
                    TetrisGame.ui.setState(TetrisGame.board.getField());
                    break;
                case hardDrop:
                    if(!TetrisGame.timer.isRunning())
                        break;
                    TetrisGame.board.removePentomino();
                    TetrisGame.board.currentPentomino.hardDrop();
                    TetrisGame.board.addPentomino();
                    TetrisGame.ui.setState(TetrisGame.board.getField());
                    break;
                case rotateRight:
                    if(!TetrisGame.timer.isRunning() || TetrisGame.isOptimalOrderActive() || TetrisGame.isBotActive())
                        break;
                    TetrisGame.board.removePentomino();
                    TetrisGame.board.currentPentomino.rotateRight();
                    TetrisGame.board.addPentomino();
                    TetrisGame.ui.setState(TetrisGame.board.getField());
                    break;
                case rotateLeft:
                    if(!TetrisGame.timer.isRunning() || TetrisGame.isOptimalOrderActive() || TetrisGame.isBotActive())
                        break;
                    TetrisGame.board.removePentomino();
                    TetrisGame.board.currentPentomino.rotateLeft();
                    TetrisGame.board.addPentomino();
                    TetrisGame.ui.setState(TetrisGame.board.getField());
                    break;
                case pause:
                    TetrisGame.pauseGame();
                    break;
            }
        }
        return true;
    }
}
