package interface_adapter;

import data_access.InMemoryProjectDataAccessObject;
import entity.Drawing;
import entity.Project;
import interface_adapter.free_draw.FreeDrawController;
import interface_adapter.free_draw.FreeDrawPresenter;
import interface_adapter.free_draw.FreeDrawViewModel;
import use_case.free_draw.FreeDrawInputBoundary;
import use_case.free_draw.FreeDrawInteractor;
import use_case.free_draw.FreeDrawOutputBoundary;
import view.CanvasView;

import javax.swing.*;
import java.awt.*;

/**
 * Temporary class to see how the canvas view looks.
 * Also wires up the Free Draw use case so you can click "Draw" and draw with the mouse.
 */
public class CanvasViewPreview {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create a dummy ViewModel
            CanvasViewModel viewModel = new CanvasViewModel();
            CanvasState canvasState = viewModel.getState();
            canvasState.setTitle("My Drawing");
            canvasState.setCanvasWidth(800);
            canvasState.setCanvasHeight(600);
            canvasState.setCanvasColour(Color.WHITE);

            // A project with one drawing layer to draw on
            Project project = new Project("1", "My Drawing");
            Drawing drawingLayer = new Drawing("Layer 1");
            project.getCanvas().addLayer(drawingLayer);

            InMemoryProjectDataAccessObject projectDataAccessObject = new InMemoryProjectDataAccessObject();
            projectDataAccessObject.setCurrentProject(project);

            FreeDrawViewModel freeDrawViewModel = new FreeDrawViewModel();
            freeDrawViewModel.getState().setActiveLayerId(drawingLayer.getLayerId());

            FreeDrawOutputBoundary freeDrawPresenter = new FreeDrawPresenter(freeDrawViewModel);
            FreeDrawInputBoundary freeDrawInteractor =
                    new FreeDrawInteractor(projectDataAccessObject, freeDrawPresenter);
            FreeDrawController freeDrawController = new FreeDrawController(freeDrawInteractor);

            // Create the view
            CanvasView canvasView = new CanvasView(viewModel, freeDrawViewModel);
            canvasView.setFreeDrawController(freeDrawController);

            JFrame frame = new JFrame("Canvas Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.add(canvasView);
            frame.setVisible(true);
        });
    }
}
