package renwu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentForm extends JDialog {
    private JTextField nameField;
    private JTextField studentIdField;
    private JTextField classNameField;
    private JTextField majorField;
    private Student result = null;

    public StudentForm(JFrame parent, String title) {
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
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        nameField = createField("例如：张三");
        studentIdField = createField("例如：2021001");
        classNameField = createField("例如：计算机科学1班");
        majorField = createField("例如：计算机科学与技术");

        addRow(formPanel, gbc, 0, "姓名", nameField);
        addRow(formPanel, gbc, 1, "学号", studentIdField);
        addRow(formPanel, gbc, 2, "班级", classNameField);
        addRow(formPanel, gbc, 3, "专业", majorField);

        wrapper.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        JButton ok = createAccentButton("确定");
        JButton cancel = createGhostButton("取消");

        buttonPanel.add(cancel);
        buttonPanel.add(ok);
        wrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);

        ok.addActionListener(e -> {
            String name = nameField.getText().trim();
            String studentId = studentIdField.getText().trim();
            if (name.isEmpty() || studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "姓名和学号不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            result = new Student(
                    name,
                    studentId,
                    classNameField.getText().trim(),
                    majorField.getText().trim()
            );
            setVisible(false);
        });

        cancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });

        setSize(420, 280);
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

    public Student showDialog() {
        setVisible(true);
        return result;
    }

    public void fillData(Student student) {
        if (student != null) {
            nameField.setText(student.getName());
            studentIdField.setText(student.getStudentId());
            classNameField.setText(student.getClassName());
            majorField.setText(student.getMajor());
        }
    }
}
