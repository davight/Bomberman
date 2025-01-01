package game.images;

import fri.shapesge.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageManager {

    private static final HashMap<String, ImageData> LOADED_IMAGES = new HashMap<>();
    private static final String PATH = "C:\\Users\\david\\IdeaProjects\\Bomberman\\src\\game\\images\\";

    public static BufferedImage getImage(String name) {

        return null;
    }

    public static void reloadImages() {
        LOADED_IMAGES.clear();
    }

}
