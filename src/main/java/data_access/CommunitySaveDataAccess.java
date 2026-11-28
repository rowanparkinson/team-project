package data_access;

import entity.*;
import use_case.publish_project.CommunityDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DAO for project data implemented using a File to convert the data.
 */
public class CommunitySaveDataAccess implements CommunityDataAccessInterface {
    private static final Path COMMUNITY_DIR = Paths.get("community_data", "published_projects");
    private static final Path PROJECTS_DIR = Paths.get("community_data", "projects");
    private static final Path IMAGES_DIR = Paths.get("community_data", "images");
    private static final Path COMMUNITY_CSV = COMMUNITY_DIR.resolve("communitydata.csv");

    @Override
    public void addToCommunity(Project project, String id, String name, String description) {
        writeData(project, id, name, description);
    }

    @Override
    public void modifyInCommunity(Project project, String id, String name, String description) {
        if (!Files.exists(COMMUNITY_CSV)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(COMMUNITY_CSV);
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (columns.length > 0 && columns[0].equals(id)) {
                    deleteOldProjectImages(id);
                    String projectFilePath = convertProjectToWriteable(project);
                    String updatedRow = id + "," + escapeCsv(name) + ","
                            + escapeCsv(description) + "," + escapeCsv(projectFilePath);
                    updatedLines.add(updatedRow);
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (found) {
                Files.write(COMMUNITY_CSV, updatedLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteOldProjectImages(String projectId) {
        File imagesFolder = IMAGES_DIR.toFile();
        File[] files = imagesFolder.listFiles();

        if (files == null) {
            return;
        }

        String prefix = projectId + "_";
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.startsWith(prefix) && fileName.endsWith(".png")) {
                file.delete();
            }
        }
    }

    private void writeData(Project project, String id, String name, String description) {
        try {
            Files.createDirectories(COMMUNITY_DIR);

            String projectFilePath = convertProjectToWriteable(project);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMMUNITY_CSV.toFile(), true))) {
                writer.write(id + "," + escapeCsv(name) + "," + escapeCsv(description) + "," + escapeCsv(projectFilePath));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertProjectToWriteable(Project project) {
        Path projectPath = PROJECTS_DIR.resolve(project.getId() + ".csv");

        try {
            Files.createDirectories(PROJECTS_DIR);
            Files.createDirectories(IMAGES_DIR);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(projectPath.toFile(), false))) {
                writeRow(project, writer);

                Canvas canvas = project.getCanvas();
                writeCanvasRow(writer, canvas);

                ColourPalette palette = project.getPalette();
                writePaletteRow(palette, writer);

                writeLayerRow(project, canvas, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projectPath.toString();
    }

    private void writeRow(Project project, BufferedWriter writer) throws IOException {
        writer.write("PROJECT" + "," +
                project.getId() + ","
                + escapeCsv(project.getName()) + ","
                + project.getIsPublished());
        writer.newLine();
    }

    private static void writeCanvasRow(BufferedWriter writer, Canvas canvas) throws IOException {
        writer.write("CANVAS" + ","
                + canvas.getWidth() + ","
                + canvas.getHeight() + ","
                + canvas.getBackgroundColor());
        writer.newLine();
    }

    private void writePaletteRow(ColourPalette palette, BufferedWriter writer) throws IOException {
        String baseColour = palette.getBaseColour() != null ? palette.getBaseColour() : "";
        String schemeType = palette.getSchemeType() != null ? escapeCsv(palette.getSchemeType()) : "";
        String colours = String.join(";", palette.getColours());
        writer.write("PALETTE" + ","
                + escapeCsv(palette.getName())  + ","
                + baseColour + ","
                + schemeType + ","
                + colours);
        writer.newLine();
    }

    private void writeLayerRow(Project project, Canvas canvas, BufferedWriter writer) throws IOException {
        for (Layer layer : canvas.getLayers()) {
            if (layer instanceof Drawing) {
                Drawing drawing = (Drawing) layer;
                writer.write("DRAWING" + ","
                        + drawing.getId() + ","
                        + escapeCsv(drawing.getName()) + ","
                        + drawing.isVisible() + ","
                        + drawing.getX() + ","
                        + drawing.getY() + ","
                        + drawing.getTransparency());
                writer.newLine();

                for (Stroke stroke : drawing.getStrokes()) {
                    List<String> pointStrings = new ArrayList<>();
                    for (Point2D.Double p : stroke.getPoints()) {
                        pointStrings.add(p.x + ":" + p.y);
                    }
                    String points = String.join(";", pointStrings);

                    writer.write("STROKE,"
                            + stroke.getColour() + ","
                            + stroke.getWidth() + ","
                            + points);
                    writer.newLine();
                }
            } else if (layer instanceof Image) {
                Image image = (Image) layer;
                Path imagePath = IMAGES_DIR.resolve(
                        project.getId() + "_" + image.getX() + "_" + image.getY() + ".png"
                );
                ImageIO.write(image.getEditedImage(), "png", imagePath.toFile());

                writer.write("IMAGE" + ","
                        + image.getId() + ","
                        + escapeCsv(image.getName()) + ","
                        + image.isVisible() + ","
                        + image.getX() + ","
                        + image.getY() + ","
                        + image.getTransparency() + ","
                        + escapeCsv(imagePath.toString()
                ));
                writer.newLine();
            }
        }
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String sanitized = value.replace("\r\n", " ").replace("\n", " ");
        if (sanitized.contains(",") || sanitized.contains("\"")) {
            sanitized = "\"" + sanitized.replace("\"", "\"\"") + "\"";
        }
        return sanitized;
    }
}
