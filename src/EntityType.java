import fri.shapesge.ImageData;

/**
 * Enum entít a ich vlastnosti a textúry. Jedná sa o entity, ktoré sa snažia zabiť hráča.
 */
public enum EntityType {

    CLASSIC(2000, "classic/staying", "classic/up_moving", "classic/down_moving", "classic/left_moving", "classic/right_moving"),
    SPEEDY(1, "speedy/staying", "speedy/up_moving", "speedy/down_moving", "speedy/left_moving", "speedy/right_moving"),
    EXPLOSIVE(4000, "explosive/staying", "explosive/up_moving", "explosive/down_moving", "explosive/left_moving", "explosive/right_moving");

    private static final String PATH = "images/entity/";

    private final int randomMovement;
    private final ImageData stayingImage;
    private final ImageData upMovingImage;
    private final ImageData downMovingImage;
    private final ImageData leftMovingImage;
    private final ImageData rightMovingImage;

    EntityType(int randomMovement, String stayingTexture, String upMovingTexture, String downMovingTexture, String leftMovingTexture, String rightMovingTexture) {
        this.stayingImage = new ImageData(PATH + stayingTexture + ".png");
        this.upMovingImage = new ImageData(PATH + upMovingTexture + ".png");
        this.downMovingImage = new ImageData(PATH + downMovingTexture + ".png");
        this.leftMovingImage = new ImageData(PATH + leftMovingTexture + ".png");
        this.rightMovingImage = new ImageData(PATH + rightMovingTexture + ".png");
        this.randomMovement = randomMovement;
    }

    /**
     * Vráti číslo, teda frekvenciu, ktorou sa má entita pohybovať (v ms).
     */
    public int getRandomMovement() {
        return this.randomMovement;
    }

    /**
     * Vráti dáta obrázku hýbajúcej sa entity smerom hore.
     */
    public ImageData getUpMovingImage() {
        return this.upMovingImage;
    }

    /**
     * Vráti dáta obrázku hýbajúcej sa entity smerom dole.
     */
    public ImageData getDownMovingImage() {
        return this.downMovingImage;
    }

    /**
     * Vráti dáta obrázku hýbajúcej sa entity smerom vľavo.
     */
    public ImageData getLeftMovingImage() {
        return this.leftMovingImage;
    }

    /**
     * Vráti dáta obrázku hýbajúcej sa entity smerom vpravo.
     */
    public ImageData getRightMovingImage() {
        return this.rightMovingImage;
    }

    /**
     * Vráti dáta obrázku stojacej entity.
     */
    public ImageData getStayingImage() {
        return this.stayingImage;
    }

}
