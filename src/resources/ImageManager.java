package resources;

import fri.shapesge.ImageData;

import java.util.HashMap;
import java.util.Objects;

public class ImageManager {

    private static final HashMap<String, ImageData> IMAGES = new HashMap<>();

    public static ImageData getImage(String path) {
        return IMAGES.computeIfAbsent(path, p -> new ImageData("resources/" + path + ".png"));
    }

    public static String[] getLoadedImages() {
        return IMAGES.keySet().stream().filter(Objects::nonNull).toArray(String[]::new);
    }

}
