package data_access;

import use_case.import_image.ImportImageDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ImageDataAccessObject implements ImportImageDataAccessInterface {
    private final Path projectsDirectory;

    public ImageDataAccessObject(Path projectsDirectory) {
        this.projectsDirectory = projectsDirectory;

    }
    @Override
    public boolean isValidImage(File file) {
        if (file == null || !file.isFile()) {
            return false;
        }
        try {
            return ImageIO.read(file) != null;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public BufferedImage readImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);

        if (image == null) {
            throw new IOException("Unable to read image file.");
        }

        return image;
    }

    @Override
    public Path saveImage(BufferedImage image, String projectId) throws IOException {
        Path imagesDirectory = projectsDirectory
                .resolve(String.valueOf(projectId))
                .resolve("images");

        Files.createDirectories(imagesDirectory);

        String fileName = UUID.randomUUID() + ".png";
        Path imagePath = imagesDirectory.resolve(fileName);

        if (!ImageIO.write(image, "png", imagePath.toFile())) {
            throw new IOException("Failed to save image to project files.");
        }

        return imagePath;
    }
}
