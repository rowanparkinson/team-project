package use_case.import_image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface ImportImageDataAccessInterface {

    boolean isValidImage(File file);

    BufferedImage readImage(File file) throws IOException;

    Path saveImage(BufferedImage image, String projectId) throws IOException;

}