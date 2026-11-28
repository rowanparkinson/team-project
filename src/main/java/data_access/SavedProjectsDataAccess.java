package data_access;

import entity.*;
import use_case.LoadedProjectDataAccessInterface;
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

/**
 * DAO for project data implemented using a File to fetch the data.
 */
public class SavedProjectsDataAccess implements LocalDataAccessInterface, LoadedProjectDataAccessInterface {
    private static final Path LOCAL_PROJECTS_CSV = Paths.get("local_data/projects.csv");
    private final Map<String, Project> loadedProjects = new HashMap<>();

    @Override
    public Project findProject(String projectId) {
        if (containsProject(projectId)) {
            return getProject(projectId);
        }
        Map<String, Project> projects = readData();
        Project project = projects.get(projectId);
        if (project != null) {
            saveProject(project);
        }
        return project;
    }

    private Map<String, Project> readData() {
        Map<String, Project> projects = new HashMap<>();

        if (!Files.exists(LOCAL_PROJECTS_CSV)) {
            return projects;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LOCAL_PROJECTS_CSV.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",", -1);
                String id = columns[0];
                String projectFilePath = columns[2];

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
                    project = getProjectRowInfo(col);

                } else if (type.equals("CANVAS") && project != null) {
                    project.setCanvas(getCanvasRowInfo(col));

                } else if (type.equals("PALETTE") && project != null) {
                    getPaletteRowInfo(project, col);

                } else if (type.equals("DRAWING") && project != null) {
                    currentDrawing = getDrawingRowInfo(col, project);

                } else if (type.equals("STROKE") && currentDrawing != null) {
                    Stroke stroke = getStrokeRowInfo(col);
                    currentDrawing.addStroke(stroke);

                } else if (type.equals("IMAGE") && project != null) {
                    Image imageLayer = getImageRowInfo(col);
                    project.getCanvas().addLayer(imageLayer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return project;
    }

    private static Project getProjectRowInfo(String[] col) {
        Project project;
        String id = col[1];
        String name = col[2];
        boolean isPublished = Boolean.parseBoolean(col[3]);

        project = new Project(name, id);
        if (isPublished && !project.getIsPublished()) {
            project.updateIsPublished();
        }
        return project;
    }

    private static Canvas getCanvasRowInfo(String[] col) {
        int width = Integer.parseInt(col[1]);
        int height = Integer.parseInt(col[2]);
        String backgroundColor = col[3];
        return new Canvas(width, height, backgroundColor);
    }

    private static void getPaletteRowInfo(Project project, String[] col) {
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
    }

    private static Drawing getDrawingRowInfo(String[] col, Project project) {
        Drawing currentDrawing;
        String layerId = col[1];
        String name = col[2];
        boolean visible = Boolean.parseBoolean(col[3]);
        int x = Integer.parseInt(col[4]);
        int y = Integer.parseInt(col[5]);
        double transparency = Double.parseDouble(col[6]);

        currentDrawing = new Drawing(name, x, y);
        if (!visible) {
            currentDrawing.toggleLayer();
        }
        currentDrawing.adjustTransparency(transparency);
        project.getCanvas().addLayer(currentDrawing);
        return currentDrawing;
    }

    private static Stroke getStrokeRowInfo(String[] col) {
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
        return stroke;
    }

    private static Image getImageRowInfo(String[] col) throws IOException {
        String layerId = col[1];
        String name = col[2];
        boolean visible = Boolean.parseBoolean(col[3]);
        int x = Integer.parseInt(col[4]);
        int y = Integer.parseInt(col[5]);
        double transparency = Double.parseDouble(col[6]);
        String imagePath = col[7];

        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        Image imageLayer = new Image(name, x, y, imagePath, bufferedImage);
        if (!visible) {
            imageLayer.toggleLayer();
        }
        imageLayer.adjustTransparency(transparency);
        return imageLayer;
    }

    @Override
    public Project getProject(String projectId) {
        return loadedProjects.get(projectId);
    }

    @Override
    public boolean containsProject(String projectId) {
        return loadedProjects.containsKey(projectId);
    }

    @Override
    public void saveProject(Project project) {
        loadedProjects.put(project.getId(), project);
    }

    @Override
    public void unloadProject(String projectId) {
        loadedProjects.remove(projectId);
    }
}