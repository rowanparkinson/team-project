package view;

import interface_adapter.publish_project.ProjectViewModelListener;
import interface_adapter.publish_project.PublishProjectController;
import interface_adapter.publish_project.ProjectViewModel;
import interface_adapter.publish_project.ProjectViewState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectView extends JPanel implements ProjectViewModelListener {
    private PublishProjectController publishProjectController;
    private final ProjectViewModel projectViewModel;
    private final JButton publishButton;

    public ProjectView(ProjectViewModel projectViewModel) {
        this.projectViewModel = projectViewModel;
        this.projectViewModel.addListener(this);

        final JLabel title = new JLabel("Project Screen");

        final JPanel buttons = new JPanel();
        this.publishButton = new JButton("publish");
        buttons.add(publishButton);

        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField(projectViewModel.getState().getName());
                JTextArea descriptionArea = new JTextArea(4, 20);
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);

                Object[] text = {
                        "Name:", nameField,
                        "Description:", new JScrollPane(descriptionArea)
                };

                int confirm = JOptionPane.showConfirmDialog(
                        ProjectView.this,
                        text,
                        "Publish Project",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (confirm == JOptionPane.OK_OPTION) {
                    final ProjectViewState state = projectViewModel.getState();
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
        ProjectViewState state = projectViewModel.getState();
        JOptionPane.showMessageDialog(ProjectView.this, state.getMessage());
    }

    public void setPublishProjectController(PublishProjectController publishProjectController) {
        this.publishProjectController = publishProjectController;
    }
}
