package game.gui;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.TextBlock;
import game.Level;
import util.ImageManager;

public class LoseLevelScreen extends StartScreen {

    private static final ImageData LOSE = ImageManager.getImage("gui/you_lost");

    private final Image image;

    public LoseLevelScreen(Level level) {
        super();

        this.image = new Image(LOSE);
        this.image.changePosition(300, 200);
    }

    @Override
    public void showAll() {
        super.showAll();
        this.image.makeVisible();
    }

    @Override
    public void hideAll() {
        super.hideAll();
        this.image.makeInvisible();
    }

}
