package com.github.zuofengzhang.flake.client.utils;

import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author averyzhang
 * @date 2021/2/19
 */
public class ImageHolder {
    private static final Logger log = LoggerFactory.getLogger(ImageHolder.class);
    private static ImageHolder instance;
    private final ConcurrentHashMap<String, Image> images;

    private ImageHolder() {
        images = new ConcurrentHashMap<>();
    }

    public static ImageHolder getInstance() {
        if (instance == null) {
            synchronized (ImageHolder.class) {
                if (instance == null) {
                    instance = new ImageHolder();
                }
            }
        }
        return instance;
    }

    public Image loadImage(String imageName) {
        if (!imageName.contains("images/")) {
            imageName = "images/" + imageName;
        }
        if (!images.containsKey(imageName)) {
            log.info("load image on missing: {}", imageName);
            Image img = new Image(Objects.requireNonNull(ImageHolder.class.getClassLoader().getResourceAsStream(imageName)));
            images.put(imageName, img);
        }
        return images.get(imageName);
    }

}
