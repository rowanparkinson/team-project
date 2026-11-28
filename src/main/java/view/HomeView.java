package view;

import entity.Project;
import entity.User;
import interface_adapter.CanvasViewModelListener;
import interface_adapter.HomeViewModel;
import interface_adapter.HomeViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JPanel implements ActionListener, HomeViewModelListener {
    private final HomeViewModel homeViewModel;
    // add controllers

    // Main panels
    private final JPanel headerPanel = new JPanel();
    private final JPanel actionsPanel = new JPanel();
    private final JPanel projectsPanel = new JPanel();
    private final JPanel projectListPanel = new JPanel();

    // Header
    private final JLabel greetingLabel = new JLabel();
    private final JButton logoutButton = new JButton("Log Out");

    // Actions
    private final JButton createProjectButton = new JButton("Create a New Project");
    private final JButton exploreCommunityButton = new JButton("Explore Community");

    // Scrollable project list
    private final JScrollPane projectsScrollPane = new JScrollPane(projectListPanel);


    public HomeView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        createHeaderPanel();
        createActionsPanel();
        createProjectsPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(projectsPanel, BorderLayout.CENTER);

        updateProjectList();
        initializeActionListeners();

    }

    private void createHeaderPanel() {
        headerPanel.setLayout(new BorderLayout());
        greetingLabel.setFont(greetingLabel.getFont().deriveFont(Font.BOLD, 24f));
        greetingLabel.setText("Hello, " + homeViewModel.getState().getUser().getDisplayName() + "!");
        headerPanel.add(greetingLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
    }

    private void createActionsPanel() {
        actionsPanel.setLayout(new GridLayout(1, 2, 15, 0));
        actionsPanel.add(createProjectButton);
        actionsPanel.add(exploreCommunityButton);
    }

    private void createProjectsPanel() {
        projectsPanel.setLayout(new BorderLayout(10, 10));

        JPanel projectsContentPanel = new JPanel(new BorderLayout(5, 5));

        JLabel projectsLabel = new JLabel("My Projects");
        projectsLabel.setFont(projectsLabel.getFont().deriveFont(Font.BOLD, 20f));

        projectListPanel.setLayout(new BoxLayout(projectListPanel, BoxLayout.Y_AXIS));
        projectListPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        projectsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        projectsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        projectsContentPanel.add(projectsLabel, BorderLayout.NORTH);
        projectsContentPanel.add(projectsScrollPane, BorderLayout.CENTER);

        projectsPanel.add(actionsPanel, BorderLayout.NORTH);
        projectsPanel.add(projectsContentPanel, BorderLayout.CENTER);
    }

    private void updateProjectList() {
        User user = homeViewModel.getState().getUser();
        projectListPanel.removeAll();

        for (Project project : user.getMyProjects()) {
            JButton projectButton = new JButton(project.getName());
            projectButton.setActionCommand("openProject:" + project.getId());

            projectButton.addActionListener(this);

            // Adjust button preview
            projectButton.setPreferredSize(new Dimension(200, 75));
            projectButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
            projectButton.setHorizontalAlignment(SwingConstants.LEFT);
            projectButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));
            projectButton.setBackground(Color.WHITE);
            projectButton.setOpaque(true);
            projectButton.setForeground(Color.BLACK);
            projectButton.setFocusPainted(false);
            projectButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            projectListPanel.add(projectButton);
            projectListPanel.add(Box.createVerticalStrut(10));
        }
        projectListPanel.revalidate();
        projectListPanel.repaint();
    }

    private void initializeActionListeners() {
        logoutButton.addActionListener(this);
        createProjectButton.addActionListener(this);
        exploreCommunityButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == logoutButton) {
            // Connect to login/logout use case

        } else if (source == createProjectButton) {
            // Connect to create project use case

        } else if (source == exploreCommunityButton) {
            // Connect to explore community use case

        } else if (source instanceof JButton) {
            // Extract the project ID from the button
            JButton projectButton = (JButton) source;
            String projectId = projectButton.getActionCommand().substring("openProject:".length());

            // Connect to open project use case
        }
    }

    @Override
    public void stateChanged() {
        updateProjectList();
    }

}
