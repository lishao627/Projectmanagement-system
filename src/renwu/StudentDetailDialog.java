package renwu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDetailDialog extends JDialog {
    private Student student;
    private DataManager dataManager;
    private DefaultListModel<ExperimentReport> reportListModel;
    private JList<ExperimentReport> reportList;
    private JTextArea reportPreviewArea;

    public StudentDetailDialog(JFrame parent, Student student, DataManager dataManager) {
        super(parent, "学生专栏 - " + student.getName(), true);
        this.student = student;
        this.dataManager = dataManager;
        this.reportListModel = new DefaultListModel<>();
        initUI();
        loadReports();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(244, 246, 252));

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        JPanel header = buildHeader();
        JPanel content = buildContent();
        JPanel actions = buildActions();

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(actions, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        setSize(900, 650);
        setLocationRelativeTo(null);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(15, 10));
        header.setOpaque(false);

        JLabel title = new JLabel(student.getName() + " 的专栏");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));

        JPanel infoPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        infoPanel.setOpaque(false);
        infoPanel.add(buildInfoLabel("学号", student.getStudentId()));
        infoPanel.add(buildInfoLabel("班级", student.getClassName()));
        infoPanel.add(buildInfoLabel("专业", student.getMajor()));
        infoPanel.add(buildInfoLabel("报告数量", String.valueOf(student.getReports().size())));

        JPanel textWrapper = new JPanel(new BorderLayout(0, 8));
        textWrapper.setOpaque(false);
        textWrapper.add(title, BorderLayout.NORTH);
        textWrapper.add(infoPanel, BorderLayout.SOUTH);

        header.add(textWrapper, BorderLayout.CENTER);
        return header;
    }

    private JLabel buildInfoLabel(String key, String value) {
        JLabel label = new JLabel(key + "：" + (value == null || value.isEmpty() ? "-" : value));
        label.setForeground(new Color(120, 130, 150));
        label.setFont(label.getFont().deriveFont(13f));
        return label;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setOpaque(false);

        reportList = new JList<>(reportListModel);
        reportList.setCellRenderer(new ReportCellRenderer());
        reportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportList.setFixedCellHeight(70);
        reportList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updatePreview(reportList.getSelectedValue());
            }
        });

        JScrollPane listScroll = new JScrollPane(reportList);
        listScroll.setBorder(BorderFactory.createTitledBorder("实验报告列表"));

        reportPreviewArea = new JTextArea();
        reportPreviewArea.setEditable(false);
        reportPreviewArea.setLineWrap(true);
        reportPreviewArea.setWrapStyleWord(true);
        reportPreviewArea.setFont(reportPreviewArea.getFont().deriveFont(13f));
        reportPreviewArea.setBorder(BorderFactory.createTitledBorder("报告预览"));

        JScrollPane previewScroll = new JScrollPane(reportPreviewArea);
        previewScroll.setBorder(BorderFactory.createTitledBorder("报告预览"));

        content.add(listScroll);
        content.add(previewScroll);

        return content;
    }

    private JPanel buildActions() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnPanel.setOpaque(false);

        JButton addBtn = createPrimaryButton("新建报告");
        JButton editBtn = createSecondaryButton("编辑报告");
        JButton delBtn = createDangerButton("删除报告");
        JButton closeBtn = createGhostButton("关闭");

        addBtn.addActionListener(e -> addReport());
        editBtn.addActionListener(e -> editReport());
        delBtn.addActionListener(e -> deleteReport());
        closeBtn.addActionListener(e -> dispose());

        btnPanel.add(closeBtn);
        btnPanel.add(delBtn);
        btnPanel.add(editBtn);
        btnPanel.add(addBtn);

        return btnPanel;
    }

    private JButton createPrimaryButton(String text) {
        return styleButton(text, new Color(64, 99, 255), Color.WHITE);
    }

    private JButton createSecondaryButton(String text) {
        return styleButton(text, new Color(241, 244, 255), new Color(64, 99, 255));
    }

    private JButton createDangerButton(String text) {
        return styleButton(text, new Color(244, 67, 54), Color.WHITE);
    }

    private JButton createGhostButton(String text) {
        return styleButton(text, Color.WHITE, new Color(120, 130, 150));
    }

    private JButton styleButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        return button;
    }

    private void loadReports() {
        reportListModel.clear();
        for (ExperimentReport report : student.getReports()) {
            reportListModel.addElement(report);
        }
        if (!reportListModel.isEmpty()) {
            reportList.setSelectedIndex(0);
        } else {
            reportPreviewArea.setText("暂无实验报告，点击\"新建报告\"开始创建。");
        }
    }

    private void updatePreview(ExperimentReport report) {
        if (report == null) {
            reportPreviewArea.setText("请选择一个报告查看详情。");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("【").append(report.getReportTitle().isEmpty() ? "未命名报告" : report.getReportTitle()).append("】\n\n");
        sb.append("实验编号：").append(report.getExperimentNumber().isEmpty() ? "-" : report.getExperimentNumber()).append("\n");
        sb.append("实验日期：").append(report.getExperimentDate().isEmpty() ? "-" : report.getExperimentDate()).append("\n\n");
        sb.append("实验目的：\n").append(report.getExperimentPurpose().isEmpty() ? "-" : report.getExperimentPurpose()).append("\n\n");
        sb.append("实验原理：\n").append(report.getExperimentPrinciple().isEmpty() ? "-" : report.getExperimentPrinciple()).append("\n\n");
        sb.append("实验步骤：\n").append(report.getExperimentSteps().isEmpty() ? "-" : report.getExperimentSteps()).append("\n\n");
        sb.append("实验结果：\n").append(report.getExperimentResult().isEmpty() ? "-" : report.getExperimentResult()).append("\n\n");
        sb.append("结果分析：\n").append(report.getExperimentAnalysis().isEmpty() ? "-" : report.getExperimentAnalysis()).append("\n\n");
        sb.append("实验结论：\n").append(report.getConclusion().isEmpty() ? "-" : report.getConclusion());

        reportPreviewArea.setText(sb.toString());
    }

    private void addReport() {
        ExperimentReportForm form = new ExperimentReportForm((JFrame) getParent(), "新建实验报告");
        ExperimentReport report = form.showDialog();
        if (report != null) {
            student.getReports().add(report);
            dataManager.saveData();
            loadReports();
            reportList.setSelectedIndex(reportListModel.getSize() - 1);
        }
    }

    private void editReport() {
        ExperimentReport selected = reportList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个报告", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ExperimentReportForm form = new ExperimentReportForm((JFrame) getParent(), "编辑实验报告");
        form.fillData(selected);
        ExperimentReport updated = form.showDialog();
        if (updated != null) {
            int index = student.getReports().indexOf(selected);
            if (index >= 0) {
                student.getReports().set(index, updated);
                dataManager.saveData();
                loadReports();
                reportList.setSelectedIndex(index);
            }
        }
    }

    private void deleteReport() {
        ExperimentReport selected = reportList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个报告", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "确定要删除该实验报告吗？",
                "删除确认",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (option == JOptionPane.YES_OPTION) {
            student.getReports().remove(selected);
            dataManager.saveData();
            loadReports();
        }
    }

    private static class ReportCellRenderer extends JPanel implements javax.swing.ListCellRenderer<ExperimentReport> {
        private final JLabel titleLabel = new JLabel();
        private final JLabel metaLabel = new JLabel();

        public ReportCellRenderer() {
            setLayout(new BorderLayout(4, 4));
            setBorder(new EmptyBorder(10, 12, 10, 12));
            setOpaque(true);

            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
            metaLabel.setForeground(new Color(120, 130, 150));
            metaLabel.setFont(metaLabel.getFont().deriveFont(12f));

            add(titleLabel, BorderLayout.NORTH);
            add(metaLabel, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ExperimentReport> list,
                                                       ExperimentReport value, int index,
                                                       boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                String title = value.getReportTitle().isEmpty() ? "未命名报告" : value.getReportTitle();
                titleLabel.setText(title);
                String meta = "编号：" + (value.getExperimentNumber().isEmpty() ? "-" : value.getExperimentNumber()) +
                        " | 日期：" + (value.getExperimentDate().isEmpty() ? "-" : value.getExperimentDate());
                metaLabel.setText(meta);
            }

            if (isSelected) {
                setBackground(new Color(64, 99, 255, 40));
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(64, 99, 255, 120), 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            } else {
                setBackground(Color.WHITE);
                setBorder(new EmptyBorder(10, 12, 10, 12));
            }
            return this;
        }
    }
}

