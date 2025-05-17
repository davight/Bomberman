package util;

import fri.shapesge.ImageData;

import java.util.HashMap;

public class ImageManager {

    private static final HashMap<String, ImageData> IMAGES = new HashMap<>();

    public static ImageData getImage(String path) {
        return IMAGES.computeIfAbsent(path, (p) -> new ImageData(path + ".png"));
    }

}
