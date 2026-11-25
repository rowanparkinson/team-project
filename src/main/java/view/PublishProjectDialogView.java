package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PublishProjectDialogView extends JDialog implements ActionListener {

    private boolean published = false;

    // Main panels
    private final JPanel mainPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();

    // Top section
    private final JLabel headerLabel = new JLabel("Publish Project");
    private final JLabel titleLabel = new JLabel("Title:");
    private final JTextField titleField = new JTextField();
    private final JLabel descriptionLabel = new JLabel("Description:");
    private final JTextArea descriptionArea = new JTextArea(4, 20);

    // Bottom section
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton publishButton = new JButton("Publish");

    public PublishProjectDialogView(Window owner, String initialTitle, JComponent canvasPreview) {
        super(owner, "Publish Project", ModalityType.APPLICATION_MODAL);
        // Center section
        this.titleField.setText(initialTitle);

        mainPanel.setLayout(new BorderLayout(0, 12));
        mainPanel.setBorder(new EmptyBorder(16, 20, 16, 20));

        createTopPanel();
        createBottomPanel();

        JScrollPane canvasScrollPane = new JScrollPane(canvasPreview);
        canvasScrollPane.setPreferredSize(new Dimension(400, 250));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(canvasScrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        initializeActionListeners();

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(owner);
    }

    private void createTopPanel() {
        topPanel.setLayout(new BorderLayout(0, 12));

        headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD, 20f));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        titleField.setAlignmentX(Component.LEFT_ALIGNMENT);

        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldsPanel.add(titleLabel);
        fieldsPanel.add(Box.createVerticalStrut(4));
        fieldsPanel.add(titleField);
        fieldsPanel.add(Box.createVerticalStrut(10));
        fieldsPanel.add(descriptionLabel);
        fieldsPanel.add(Box.createVerticalStrut(4));
        fieldsPanel.add(descScrollPane);

        topPanel.add(headerLabel, BorderLayout.NORTH);
        topPanel.add(fieldsPanel, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
        bottomPanel.add(cancelButton);
        bottomPanel.add(publishButton);
    }

    private void initializeActionListeners() {
        cancelButton.addActionListener(this);
        publishButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == cancelButton) {
            dispose();
        } else if (source == publishButton) {
            published = true;
            dispose();
        }
    }

    public boolean isPublished() {
        return published;
    }

    public String getPublishName() {
        return titleField.getText();
    }

    public String getPublishDescription() {
        return descriptionArea.getText();
    }
}