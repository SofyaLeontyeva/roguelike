package entity;

import java.util.Random;
import logic.GamePanel;

public class Npc extends Entity {

    public Npc(GamePanel gamePanel) {
        super(gamePanel);
        setDirectionHeight("up");
        setDirectionWidth("right");
        setSpeed(2);
        setDialogue();
        getImage();
    }

    public void getImage() {
        setBufferedImageContainer(setup("/npc/up1"), 0);
        setBufferedImageContainer(setup("/npc/down1"), 1);
        setBufferedImageContainer(setup("/npc/left1"), 2);
        setBufferedImageContainer(setup("/npc/right1"), 3);
        setBufferedImageContainer(setup("/npc/up2"), 4);
        setBufferedImageContainer(setup("/npc/down2"), 5);
        setBufferedImageContainer(setup("/npc/left2"), 6);
        setBufferedImageContainer(setup("/npc/right2"), 7);
    }

    public void setDialogue() {
        dialogues[0] = "Hello, Ivan!";
        dialogues[1] = "How is it going?";
        dialogues[2] = "So you've come here to \nfind some treasure?";
        dialogues[3] = "Maybe one day you will \nfind your favorite mead \nin this forest";
        dialogues[4] = "Well, good luck to you...";
    }

    public void setAction() {

        setActionLockCounter(getActionLockCounter() + 1);

        if (getActionLockCounter() == 90) {
            Random random = new Random();
            int i = random.nextInt(100);

            switch (i % 16) {
                case 0 -> {
                    setDirectionHeight("up");
                    setDirectionWidth("right");
                }
                case 1 -> {
                    setDirectionHeight("up");
                    setDirectionWidth("left");
                }
                case 2 -> {
                    setDirectionHeight("down");
                    setDirectionWidth("right");
                }
                case 3 -> {
                    setDirectionHeight("down");
                    setDirectionWidth("left");
                }
                case 4, 8, 12 -> {
                    setDirectionHeight("neutral");
                    setDirectionWidth("right");
                }
                case 5, 9, 13 -> {
                    setDirectionHeight("neutral");
                    setDirectionWidth("left");
                }
                case 6, 10, 14 -> {
                    setDirectionHeight("down");
                    setDirectionWidth("neutral");
                }
                case 7, 11, 15 -> {
                    setDirectionHeight("up");
                    setDirectionWidth("neutral");
                }
                default -> {
                    // Do nothing
                }
            }
            setActionLockCounter(0);
        }

    }

    public void speak() {
        super.speak();
    }
}
