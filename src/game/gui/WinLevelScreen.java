package game.gui;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Rectangle;
import fri.shapesge.TextBlock;
import game.Level;
import util.ImageManager;

/**
 * Trieda na zobrazenie GUI, ktore sa ukaze ked hrac alebo hraci vyhraju level.
 */
public class WinLevelScreen {

    private static final ImageData WIN = ImageManager.getImage("gui/you_won");

    private final Image image;
    private final Rectangle background;
    private final TextBlock time;

    /**
     * Inicializuje nove okno s pripravenymi obrazkami.
     * @param level level, ktory hrac/i vyhral/i
     */
    public WinLevelScreen(Level level) {
        this.background = new Rectangle(0, 0);
        this.background.changeColor("white");
        this.background.changeSize(1000, 750);

        this.image = new Image(WIN);
        this.image.changePosition(300, 200);

        this.time = new TextBlock("Completion time: " + formatDuration(level.getLevelTime()));
        this.time.changePosition(300, 200 + WIN.getHeight() + 20);
    }

    /**
     * Zobrazi vsetky prvky tohto GUI.
     */
    public void showAll() {
        this.background.makeVisible();
        this.time.makeVisible();
        this.image.makeVisible();
    }

    /**
     * Schova vsetky prvky tohto GUI.
     */
    public void hideAll() {
        this.background.makeInvisible();
        this.time.makeInvisible();
        this.image.makeInvisible();
    }

    /**
     * S touto metodou som si pomohol zo StackOverFlowu. NEPISAL SOM JU JA.
     * <a href="https://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss">odkaz na diskusiu</a>
     * @param durMs dlzka na foramtovanie v milisekundach
     * @return naformatovany text
     */
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
