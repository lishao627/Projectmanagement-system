package renwu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProjectForm extends JDialog {

    private JTextField nameField;
    private JTextField typeField;
    private JTextField authorField;
    private JTextField dateField;
    private JTextArea descArea;

    private Project result = null;

    public ProjectForm(JFrame parent, String title) {
        super(parent, title, true);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        nameField = createField("例如：营销网站升级");
        typeField = createField("Web / APP / 数据分析");
        authorField = createField("负责人姓名");
        dateField = createField("2025-01-01");
        descArea = new JTextArea(4, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(8, 8, 8, 8)
        ));

        addRow(formPanel, gbc, 0, "名称", nameField);
        addRow(formPanel, gbc, 1, "类型", typeField);
        addRow(formPanel, gbc, 2, "作者", authorField);
        addRow(formPanel, gbc, 3, "日期", dateField);
        addAreaRow(formPanel, gbc, 4, "描述", new JScrollPane(descArea));

        wrapper.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        JButton ok = createAccentButton("确定");
        JButton cancel = createGhostButton("取消");

        buttonPanel.add(cancel);
        buttonPanel.add(ok);
        wrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);

        ok.addActionListener(e -> {
            result = new Project(
                    nameField.getText().trim(),
                    typeField.getText().trim(),
                    authorField.getText().trim(),
                    dateField.getText().trim(),
                    descArea.getText().trim()
            );
            setVisible(false);
        });

        cancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });

        setSize(420, 360);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(buildLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void addAreaRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent area) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(buildLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(area, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
    }

    private JLabel buildLabel(String text) {
        JLabel label = new JLabel(text + "：");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setForeground(new Color(110, 120, 140));
        return label;
    }

    private JTextField createField(String placeholder) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        field.setToolTipText(placeholder);
        return field;
    }

    private JButton createAccentButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(64, 99, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        return button;
    }

    private JButton createGhostButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(110, 120, 140));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        return button;
    }

    public Project showDialog() {
        setVisible(true);
        return result;
    }
}
