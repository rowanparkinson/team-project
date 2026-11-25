package data_access;

import entity.*;
import use_case.publish_project.LocalDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SavedProjectsDataAccess implements LocalDataAccessInterface {
    private static final Path COMMUNITY_DIR = Paths.get("community_data", "published_projects");
    private static final Path COMMUNITY_CSV = COMMUNITY_DIR.resolve("communitydata.csv");

    @Override
    public Project findProject(long projectId) {
        Map<Long, Project> projects = readData();
        return projects.get(projectId);
    }

    private Map<Long, Project> readData() {
        Map<Long, Project> projects = new HashMap<>();

        // If nothing has been published yet, return an empty map
        if (!Files.exists(COMMUNITY_CSV)) {
            return projects;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(COMMUNITY_CSV.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] columns = line.split(",", -1);
                long id = Long.parseLong(columns[0]);
                String projectFilePath = columns[3];

                Project project = readProjectFromFile(projectFilePath);
                if (project != null) {
                    projects.put(id, project);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projects;
    }

    private Project readProjectFromFile(String filePath) {
        Project project = null;
        Drawing currentDrawing = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] col = line.split(",", -1);
                String type = col[0];

                if (type.equals("PROJECT")) {
                    long id = Long.parseLong(col[1]);
                    String name = col[2];
                    boolean isPublished = Boolean.parseBoolean(col[3]);

                    project = new Project(id, name);
                    if (isPublished && !project.getIsPublished()) {
                        project.updateIsPublished();
                    }

                } else if (type.equals("CANVAS") && project != null) {
                    int width = Integer.parseInt(col[1]);
                    int height = Integer.parseInt(col[2]);
                    String backgroundColor = col[3];
                    project.setCanvas(new Canvas(width, height, backgroundColor));

                } else if (type.equals("PALETTE") && project != null) {
                    ColourPalette palette = project.getPalette();
                    palette.setName(col[1]);
                    String baseColour = col[2];
                    String schemeType = col[3];

                    List<String> colours = new ArrayList<>();
                    if (!col[4].isEmpty()) {
                        String[] colourArray = col[4].split(";");
                        colours.addAll(Arrays.asList(colourArray));
                    }

                    if (!baseColour.isEmpty() && !schemeType.isEmpty()) {
                        palette.generate(baseColour, schemeType, colours);
                    } else {
                        palette.setColours(colours);
                    }

                } else if (type.equals("DRAWING") && project != null) {
                    String name = col[1];
                    boolean visible = Boolean.parseBoolean(col[2]);
                    int position = Integer.parseInt(col[3]);
                    double transparency = Double.parseDouble(col[4]);

                    currentDrawing = new Drawing(position, name);
                    if (!visible) {
                        currentDrawing.toggleLayer();
                    }
                    currentDrawing.adjustTransparency(transparency);
                    project.getCanvas().addLayer(currentDrawing);
                } else if (type.equals("STROKE") && currentDrawing != null) {
                    String colour = col[1];
                    double width = Double.parseDouble(col[2]);
                    Stroke stroke = new Stroke(colour, width);

                    if (!col[3].isEmpty()) {
                        String[] pointPairs = col[3].split(";");
                        for (String pair : pointPairs) {
                            String[] xy = pair.split(":");
                            double x = Double.parseDouble(xy[0]);
                            double y = Double.parseDouble(xy[1]);
                            stroke.addPoint(x, y);
                        }
                    }
                    currentDrawing.addStroke(stroke);
                } else if (type.equals("IMAGE") && project != null) {
                    String name = col[1];
                    boolean visible = Boolean.parseBoolean(col[2]);
                    int position = Integer.parseInt(col[3]);
                    double transparency = Double.parseDouble(col[4]);
                    String imagePath = col[5];

                    BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
                    Image imageLayer = new Image(name, position, imagePath, bufferedImage);
                    if (!visible) {
                        imageLayer.toggleLayer();
                    }
                    imageLayer.adjustTransparency(transparency);
                    project.getCanvas().addLayer(imageLayer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return project;
    }
}
