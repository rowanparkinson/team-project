package view;

import interface_adapter.publish_project.CanvasViewModelListener;
import interface_adapter.publish_project.PublishProjectController;
import interface_adapter.publish_project.CanvasViewModel;
import interface_adapter.publish_project.CanvasViewState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CanvasView extends JPanel implements CanvasViewModelListener {
    private PublishProjectController publishProjectController;
    private final CanvasViewModel canvasViewModel;
    private final JButton publishButton;

    public CanvasView(CanvasViewModel canvasViewModel) {
        this.canvasViewModel = canvasViewModel;
        this.canvasViewModel.addListener(this);

        final JLabel title = new JLabel("Project Screen");

        final JPanel buttons = new JPanel();
        this.publishButton = new JButton("publish");
        buttons.add(publishButton);

        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField(canvasViewModel.getState().getPublishName());
                JTextArea descriptionArea = new JTextArea(4, 20);
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);

                Object[] text = {
                        "Name:", nameField,
                        "Description:", new JScrollPane(descriptionArea)
                };

                int confirm = JOptionPane.showConfirmDialog(
                        CanvasView.this,
                        text,
                        "Publish Project",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (confirm == JOptionPane.OK_OPTION) {
                    final CanvasViewState state = canvasViewModel.getState();
                    final String publishName = nameField.getText();
                    final String publishDescription = descriptionArea.getText();
                    publishProjectController.execute(state.getProjectId(), publishName, publishDescription);
                }
            }
        });

        this.add(title);
        this.add(buttons);
    }

    @Override
    public void stateChanged() {
        final String message;
        CanvasViewState state = canvasViewModel.getState();
        JOptionPane.showMessageDialog(CanvasView.this, state.getPublishMessage());
    }

    public void setPublishProjectController(PublishProjectController publishProjectController) {
        this.publishProjectController = publishProjectController;
    }
}
