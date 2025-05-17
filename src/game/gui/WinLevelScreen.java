package game.gui;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Rectangle;
import fri.shapesge.TextBlock;
import game.Level;
import util.ImageManager;

public class WinLevelScreen {

    private static final ImageData WIN = ImageManager.getImage("gui/you_won");

    private final Image image;
    private final Rectangle background;
    private final TextBlock time;

    public WinLevelScreen(Level level) {
        this.background = new Rectangle(0, 0);
        this.background.changeColor("white");
        this.background.changeSize(1000, 750);

        this.image = new Image(WIN);
        this.image.changePosition(300, 200);

        this.time = new TextBlock("Completion time: " + formatDuration(level.getLevelTime()));
        this.time.changePosition(300, 200 + WIN.getHeight() + 20);
    }

    public void showAll() {
        this.background.makeVisible();
        this.time.makeVisible();
        this.image.makeVisible();
    }

    public void hideAll() {
        this.background.makeInvisible();
        this.time.makeInvisible();
        this.image.makeInvisible();
    }

    private static String formatDuration(long durMs) {
        StringBuilder f = new StringBuilder();
        int sec = (int)(durMs / 1000) % 60;
        int min = (int)((durMs / (1000 * 60)) % 60);
        int hod = (int)((durMs / (1000 * 60 * 60)) % 24);

        if (hod > 0) {
            f.append(hod + "h ");
        }
        if (min > 0) {
            f.append(min + "m ");
        }
        if (sec > 0) {
            f.append(sec + "s");
        }

        return f.toString();
    }

}
