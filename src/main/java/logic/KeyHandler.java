package logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean enterPressed;
    private int[] currentControlScheme;
    private GamePanel gamepanel;
    private final int[][] controlSchemes = {
            {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_P},
            {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_P}
    };

    public KeyHandler(GamePanel innnerGamePanel) {
        currentControlScheme = controlSchemes[0];
        this.gamepanel = innnerGamePanel;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == currentControlScheme[0]) {
            upPressed = false;
        }

        if (code == currentControlScheme[1]) {
            downPressed = false;
        }

        if (code == currentControlScheme[2]) {
            leftPressed = false;
        }

        if (code == currentControlScheme[3]) {
            rightPressed = false;
        }


    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == currentControlScheme[4]) {
            if (gamepanel.getGameState() == gamepanel.getPlayState()) {
                gamepanel.setGameState(gamepanel.getPauseState());
            } else if (gamepanel.getGameState() == gamepanel.getPauseState()) {
                gamepanel.setGameState(gamepanel.getPlayState());
            }
        }

        if (gamepanel.getGameState() == gamepanel.getPlayState()) {
            if (code == currentControlScheme[0]) {
                upPressed = true;
            }

            if (code == currentControlScheme[1]) {
                downPressed = true;
            }

            if (code == currentControlScheme[2]) {
                leftPressed = true;
            }

            if (code == currentControlScheme[3]) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            if (code == KeyEvent.VK_EQUALS) {
                int currentIndex = java.util.Arrays.asList(controlSchemes).indexOf(currentControlScheme);
                int newIndex = (currentIndex + 1) % controlSchemes.length;
                switchControlScheme(newIndex);
            }
        }

        if (gamepanel.getGameState() == gamepanel.getDialogueState()) {
            if (code == KeyEvent.VK_ENTER) {
                gamepanel.setGameState(gamepanel.getPlayState());
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void switchControlScheme(int index) {
        if (index >= 0 && index < controlSchemes.length) {
            currentControlScheme = controlSchemes[index];
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }

    public GamePanel getGamepanel() {
        return gamepanel;
    }
}
