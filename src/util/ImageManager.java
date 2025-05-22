package util;

import fri.shapesge.ImageData;

import java.util.HashMap;

/**
 * Pomocna trieda ImageManager na pristupovanie ku cachovanym obrazkom.
 */
public class ImageManager {

    private static final HashMap<String, ImageData> IMAGES = new HashMap<>();

    /**
     * Vrati data obrazku vzhladom na jeho path.
     * @param path cesta k obrazku
     * @return data obrazku
     */
    public static ImageData getImage(String path) {
        return IMAGES.computeIfAbsent(path, (p) -> new ImageData(path + ".png"));
    }

}
