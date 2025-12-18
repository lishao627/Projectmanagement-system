package renwu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExperimentReportForm extends JDialog {
    private JTextField titleField;
    private JTextField numberField;
    private JTextField dateField;
    private JTextArea purposeArea;
    private JTextArea principleArea;
    private JTextArea stepsArea;
    private JTextArea resultArea;
    private JTextArea analysisArea;
    private JTextArea conclusionArea;
    private ExperimentReport result = null;

    public ExperimentReportForm(JFrame parent, String title) {
        super(parent, title, true);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        titleField = createField("例如：Java程序设计实验一");
        numberField = createField("例如：实验1");
        dateField = createField("例如：2025-01-15");

        purposeArea = createTextArea(3, "实验目的：通过本次实验，掌握...");
        principleArea = createTextArea(3, "实验原理：...");
        stepsArea = createTextArea(4, "实验步骤：\n1. ...\n2. ...");
        resultArea = createTextArea(4, "实验结果：...");
        analysisArea = createTextArea(4, "结果分析：...");
        conclusionArea = createTextArea(3, "实验结论：...");

        int row = 0;
        addRow(formPanel, gbc, row++, "报告标题", titleField);
        addRow(formPanel, gbc, row++, "实验编号", numberField);
        addRow(formPanel, gbc, row++, "实验日期", dateField);
        addAreaRow(formPanel, gbc, row++, "实验目的", purposeArea);
        addAreaRow(formPanel, gbc, row++, "实验原理", principleArea);
        addAreaRow(formPanel, gbc, row++, "实验步骤", stepsArea);
        addAreaRow(formPanel, gbc, row++, "实验结果", resultArea);
        addAreaRow(formPanel, gbc, row++, "结果分析", analysisArea);
        addAreaRow(formPanel, gbc, row++, "实验结论", conclusionArea);

        wrapper.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        JButton ok = createAccentButton("保存");
        JButton cancel = createGhostButton("取消");

        buttonPanel.add(cancel);
        buttonPanel.add(ok);
        wrapper.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        ok.addActionListener(e -> {
            result = new ExperimentReport();
            result.setReportTitle(titleField.getText().trim());
            result.setExperimentNumber(numberField.getText().trim());
            result.setExperimentDate(dateField.getText().trim());
            result.setExperimentPurpose(purposeArea.getText().trim());
            result.setExperimentPrinciple(principleArea.getText().trim());
            result.setExperimentSteps(stepsArea.getText().trim());
            result.setExperimentResult(resultArea.getText().trim());
            result.setExperimentAnalysis(analysisArea.getText().trim());
            result.setConclusion(conclusionArea.getText().trim());
            setVisible(false);
        });

        cancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });

        setSize(700, 750);
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

    private void addAreaRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextArea area) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(buildLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(4, 4, 4, 4)
        ));
        panel.add(scrollPane, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
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

    private JTextArea createTextArea(int rows, String placeholder) {
        JTextArea area = new JTextArea(rows, 30);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        area.setToolTipText(placeholder);
        return area;
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

    public ExperimentReport showDialog() {
        setVisible(true);
        return result;
    }

    public void fillData(ExperimentReport report) {
        if (report != null) {
            titleField.setText(report.getReportTitle());
            numberField.setText(report.getExperimentNumber());
            dateField.setText(report.getExperimentDate());
            purposeArea.setText(report.getExperimentPurpose());
            principleArea.setText(report.getExperimentPrinciple());
            stepsArea.setText(report.getExperimentSteps());
            resultArea.setText(report.getExperimentResult());
            analysisArea.setText(report.getExperimentAnalysis());
            conclusionArea.setText(report.getConclusion());
        }
    }
}

