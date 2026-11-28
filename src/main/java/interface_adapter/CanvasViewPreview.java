package interface_adapter;

import view.CanvasView;

import javax.swing.*;
import java.awt.*;

/**
 * Temporary class to see how the canvas view looks.
 */
public class CanvasViewPreview {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create a dummy ViewModel
            CanvasViewModel viewModel = new CanvasViewModel();
            CanvasState canvasState = new CanvasState();
            canvasState.setTitle("My Drawing");
            canvasState.setCanvasWidth(800);
            canvasState.setCanvasHeight(600);
            canvasState.setCanvasColour(Color.WHITE);
            viewModel.setState(new CanvasState());

            // Create the view
            CanvasView canvasView = new CanvasView(viewModel);

            // Display it
            JFrame frame = new JFrame("Canvas Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.add(canvasView);
            frame.setVisible(true);
        });
    }
}