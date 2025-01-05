import fri.shapesge.FontStyle;
import fri.shapesge.Manager;
import fri.shapesge.Rectangle;
import fri.shapesge.TextBlock;

import java.util.ArrayList;

/**
 * Trieda GameCanvas, ktorá predstavuje herné plátno a má za účel spravovať akcie, ktoré sa na ňom dejú.
 */
public class GameCanvas {

    // Vzhľadom na to, že fri.shapesge.Manager spravuje objekty staticky od fri.shapesge.Game tak som si vyrobil vlastný
    // zoznam spravovaných objektov, ktoré pri začatí novej hry vždy premažem.
    private static final ArrayList<Object> MANAGED_OBJECTS = new ArrayList<>();

    private final Manager gameManager = new Manager();
    private final Tile[][] tiles;
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Bomb> bombs = new ArrayList<>();
    private final Rectangle finalScreen = new Rectangle(0, 0);
    private final long start;

    private Player player = null;
    private boolean isPlaying = true;

    /**
     * Inicializuje nové herné plátno.
     * @param blocks Block array s n*m prvkami
     */
    public GameCanvas(BlockType[][] blocks) {
        this.clearManagedObjects();

        this.gameManager.manageObject(this);
        this.tiles = new Tile[blocks.length][blocks[0].length];
        this.start = System.currentTimeMillis();
        this.finalScreen.changeSize(950, 550);

        for (int y = 0; y < blocks.length; y++) {
            for (int x = 0; x < blocks[y].length; x++) {
                this.tiles[y][x] = new Tile(x, y, blocks[y][x], this);
            }
        }
    }

    /**
     * Vráti Tile na daných súradniciach array Tilov herného plátna. Null ak neexistuje.
     *
     * @param x x-ová súradnica
     * @param y y-ová súradnica
     */
    public Tile getTileAtBoard(int x, int y) {
        if (y < 0 || y >= this.tiles.length || x < 0 || x >= this.tiles[y].length) {
            return null;
        }
        return this.tiles[y][x];
    }

    /**
     * Vráti Tile na daných reálnych súradniciach herného plátna. Null ak neexistuje.
     *
     * @param x x-ová súradnica
     * @param y y-ová súradnica
     */
    public Tile getTileAtCoords(int x, int y) {
        return this.getTileAtBoard(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
    }

    ///////////////
    /// Entities
    /////////////

    /**
     * Vytvorí novú entitu daného typu a položí ju na daný Tile.
     *
     * @param type typ entity, ktorá bude vytvorená
     * @param at Tile (miesto) kde bude entita vytvorená
     */
    public void spawnEntity(EntityType type, Tile at) {
        if (at.entityEnterTile()) {
            Entity e = new Entity(type, at);
            this.manageObject(e);
            this.entities.add(e);
        }
    }

    /**
     * Zabije a zmaže danú entitu z plátna.
     *
     * @param entity entita, ktorá bude zmazaná
     */
    public void killEntity(Entity entity) {
        this.entities.remove(entity);
        entity.die();
        if (this.entities.isEmpty() && this.player.getHealth() > 0) {
            this.win();
        }
    }

    /**
     * Vráti zoznam entít, ktoré sa aktívne nachádzajú na plátne.
     */
    public Entity[] getEntities() {
        return this.entities.toArray(new Entity[0]);
    }

    /////////////
    /// Player
    ///////////

    /**
     * Vytvorí nového hráča a položí ju na daný tile.
     *
     * @param at Tile (miesto) kde bude hráč vytvorený
     */
    public void spawnPlayer(Tile at) {
        if (at.playerEnterTile()) {
            Player p = new Player(at);
            this.manageObject(p);
            this.player = p;
        }
    }

    /**
     * Zabije aktívneho hráča.
     */
    public void killPlayer() {
        this.player.takeDamage(this.player.getHealth());
        this.player = null;
    }

    /**
     * Vráti aktívneho hráča na hernom plátne.
     */
    public Player getPlayer() {
        return this.player;
    }

    //////////
    /// Bomb
    ////////

    /**
     * Vytvorí, aktivuje a položí novú bombu na daný tile.
     * Následne vráti boolean hodnotu či sa podarilo bombu vytvoriť.
     *
     * @param at Tile (miesto) kde bude bomba vytvorená
     */
    public boolean spawnBomb(Tile at) {
        if (at.bombEnterTile()) {
            Bomb b = new Bomb(at);
            this.manageObject(b);
            this.bombs.add(b);
            return true;
        }
        return false;
    }

    /**
     * Zmaže danú bombu z herného plátna.
     *
     * @param bomb bomba, ktorá bude zmazaná
     */
    public void removeBomb(Bomb bomb) {
        this.bombs.remove(bomb);
        this.stopManagingObject(bomb);
    }

    /**
     * Vráti zoznam bomb, ktoré sa aktívne nachádzajú na plátne
     */
    public Bomb[] getBombs() {
        return this.bombs.toArray(new Bomb[0]);
    }

    /**
     * Vráti hodnotu, či na plátne ešte prebieha hra.
     */
    public boolean isPlaying() {
        return this.isPlaying;
    }

    /**
     * Pridá objekt Managerovi na spravovanie.
     */
    public void manageObject(Object object) {
        MANAGED_OBJECTS.add(object);
        this.gameManager.manageObject(object);
    }

    /**
     * Odoberie spravovaný objekt od Managera.
     *
     * @param object objekt, ktorý sa prestane spravovať
     */
    public void stopManagingObject(Object object) {
        MANAGED_OBJECTS.remove(object);
        this.gameManager.stopManagingObject(object);
    }

    /**
     * Premaže všetky spravované objekty.
     */
    public void clearManagedObjects() {
        for (Object object : MANAGED_OBJECTS) {
            this.gameManager.stopManagingObject(object);
        }
        MANAGED_OBJECTS.clear();
    }

    /**
     * Ukončí hru so stavom prehry.
     */
    public void lose() {
        if (!this.isPlaying) {
            return;
        }
        this.player = null;
        this.isPlaying = false;
        long duration = System.currentTimeMillis() - this.start;
        this.finalScreen.makeVisible();
        TextBlock text = new TextBlock("Prehral si!", 350, 250);
        text.changeFont("Arial", FontStyle.BOLD, 50);
        text.makeVisible();
        TextBlock time = new TextBlock("čas " + formatDuration(duration), 420, 300);
        time.changeFont("Arial", FontStyle.BOLD, 30);
        time.makeVisible();
        this.newGameButton();
    }

    /**
     * Ukončí hru so stavom výhry
     */
    public void win() {
        if (!this.isPlaying) {
            return;
        }
        this.player = null;
        this.isPlaying = false;
        long duration = System.currentTimeMillis() - this.start;
        this.finalScreen.makeVisible();
        TextBlock text = new TextBlock("Vyhral si!", 355, 250);
        text.changeFont("Arial", FontStyle.BOLD, 50);
        text.makeVisible();
        TextBlock time = new TextBlock("čas " + formatDuration(duration), 420, 300);
        time.changeFont("Arial", FontStyle.BOLD, 30);
        time.makeVisible();
        this.newGameButton();
    }

    private static String formatDuration(long duration) {
        long totalSeconds = duration / 1000;
        String s = "";
        long minutes = totalSeconds / 60;
        if (minutes > 0) {
            s += minutes + "m ";
        }
        long seconds = totalSeconds % 60;
        if (seconds > 0) {
            s += seconds + "s";
        }
        return s;
    }

    private void newGameButton() {
        Rectangle button = new Rectangle(394, 420);
        button.changeColor("yellow");
        button.changeSize(150, 50);
        button.makeVisible();
        TextBlock newGame = new TextBlock("Hrať znova", 403, 450);
        newGame.changeFont("Arial", FontStyle.BOLD, 25);
        newGame.makeVisible();
    }

    /**
     * ShapesGE listener kliknutia myšou.
     *
     * @param x x-ová súradnica kliku
     * @param y y-ová súradnica kliku
     */
    public void leftClick(int x, int y) {
        if (!this.isPlaying && x >= 394 && x <= 544 && y >= 420 && y <= 470) {

            Main.startGame();
        }
    }

}
