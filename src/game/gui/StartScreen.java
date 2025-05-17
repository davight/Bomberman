package game.gui;

import fri.shapesge.Image;
import fri.shapesge.ImageData;
import fri.shapesge.Rectangle;
import game.Game;
import util.ImageManager;
import util.ShapesGeListener;

public class StartScreen {

    private static final ImageData SOLO_GAME = ImageManager.getImage("gui/solo_game");
    private static final ImageData DUO_GAME = ImageManager.getImage("gui/duo_game");

    private final Rectangle background;
    private final Image startSoloGame;
    private final Image startDuoGame;
    private final int soloX;
    private final int soloY;
    private final int duoX;
    private final int duoY;

    public StartScreen() {
        this.background = new Rectangle(0, 0);
        this.background.changeSize(1000, 750);
        this.background.changeColor("white");

        this.startSoloGame = new Image(SOLO_GAME);
        this.soloX = 100;
        this.soloY = 500;
        this.startSoloGame.changePosition(this.soloX, this.soloY);

        this.startDuoGame = new Image(DUO_GAME);
        this.duoX = this.soloX + SOLO_GAME.getWidth() + 200;
        this.duoY = 500;
        this.startDuoGame.changePosition(this.duoX, this.duoY);
    }

    public void showAll() {
        Game.manageObject(this);
        this.background.makeVisible();
        this.startSoloGame.makeVisible();
        this.startDuoGame.makeVisible();
    }

    public void hideAll() {
        Game.stopManagingObject(this);
        this.background.makeInvisible();
        this.startSoloGame.makeInvisible();
        this.startDuoGame.makeInvisible();
    }

    @ShapesGeListener
    public void leftClick(int x, int y) {
        if (x >= this.soloX && x <= (this.soloX + SOLO_GAME.getWidth())
                && y >= this.soloY && y <= (this.soloY + SOLO_GAME.getHeight())) {
            Game.stopManagingObject(this);
            this.hideAll();
            Game.startSoloGame();
        } else if (x >= this.duoX && x <= (this.duoX + DUO_GAME.getWidth())
            && y >= this.duoY && y <= (this.duoY + DUO_GAME.getHeight())) {
            Game.stopManagingObject(this);
            this.hideAll();
            Game.startDuoGame();
        }
    }

}
