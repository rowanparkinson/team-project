package data_access;

import use_case.import_image.ImportImageDataAccessInterface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageDataAccessObject implements ImportImageDataAccessInterface {
    @Override
    public BufferedImage readImage(File file) throws IOException {
        return null;
    }

    @Override
    public boolean isValidImage(File file) {
        return false;
    }

    @Override
    public Path saveImage(BufferedImage image, String projectId) throws IOException {
        return null;
    }
}
