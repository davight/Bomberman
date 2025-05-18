package game.gui;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import game.Level;
import util.ImageManager;

/**
 * Trieda na zobrazenie GUI, ktore sa ukaze ked hrac alebo hraci prehraju level.
 */
public class LoseLevelScreen extends StartScreen {

    private static final ImageData LOSE = ImageManager.getImage("gui/you_lost");

    private final Image image;

    /**
     * Inicializuje nove okno s pripravenymi obrazkami.
     * @param level level, ktory hrac/i prehral/i
     */
    public LoseLevelScreen(Level level) {
        super();

        this.image = new Image(LOSE);
        this.image.changePosition(300, 200);
    }

    /**
     * Zobrazi vsetky prvky tohto GUI.
     */
    @Override
    public void showAll() {
        super.showAll();
        this.image.makeVisible();
    }

    /**
     * Schova vsetky prvky tohto GUI.
     */
    @Override
    public void hideAll() {
        super.hideAll();
        this.image.makeInvisible();
    }

}
