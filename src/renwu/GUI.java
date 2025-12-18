package renwu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI extends JFrame {

    private final DataManager manager = new DataManager();
    private final DefaultListModel<Student> listModel = new DefaultListModel<>();
    private JList<Student> studentListUI;
    private JTextField searchField;
    private JLabel nameValue;
    private JLabel studentIdValue;
    private JLabel classNameValue;
    private JLabel majorValue;
    private JLabel reportCountValue;

    public GUI() {
        applyLookAndFeel();
        setTitle("学生管理系统");
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadList();
    }

    private void applyLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void initUI() {
        getContentPane().setBackground(new Color(244, 246, 252));
        setLayout(new BorderLayout());

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
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(15, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("学生管理面板");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        JLabel subtitle = new JLabel("管理学生信息，查看和编辑实验报告");
        subtitle.setForeground(new Color(120, 130, 150));

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setOpaque(false);
        textWrapper.add(title, BorderLayout.NORTH);
        textWrapper.add(subtitle, BorderLayout.SOUTH);

        JPanel searchWrapper = new JPanel(new BorderLayout(8, 0));
        searchWrapper.setOpaque(false);
        searchField = new JTextField();
        searchField.setToolTipText("输入姓名 / 学号 / 班级 / 专业后回车搜索");
        JButton searchBtn = createPrimaryButton("搜索");
        searchBtn.addActionListener(e -> searchStudent());
        searchField.addActionListener(e -> searchStudent());

        searchWrapper.add(searchField, BorderLayout.CENTER);
        searchWrapper.add(searchBtn, BorderLayout.EAST);

        header.add(textWrapper, BorderLayout.CENTER);
        header.add(searchWrapper, BorderLayout.EAST);

        return header;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setOpaque(false);

        studentListUI = new JList<>(listModel);
        studentListUI.setCellRenderer(new StudentCellRenderer());
        studentListUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentListUI.setFixedCellHeight(90);
        studentListUI.setOpaque(false);
        studentListUI.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails(studentListUI.getSelectedValue());
            }
        });
        studentListUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openStudentDetail();
                }
            }
        });

        JScrollPane listScroll = new JScrollPane(studentListUI);
        listScroll.setBorder(BorderFactory.createEmptyBorder());
        listScroll.getViewport().setOpaque(false);
        listScroll.setOpaque(false);

        content.add(listScroll);
        content.add(buildDetailPanel());
        return content;
    }

    private JPanel buildDetailPanel() {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout(10, 15));
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 240), 1, true),
                new EmptyBorder(18, 18, 18, 18)
        ));
        detailPanel.setBackground(Color.WHITE);

        JLabel sectionTitle = new JLabel("学生详情");
        sectionTitle.setFont(sectionTitle.getFont().deriveFont(Font.BOLD, 20f));

        JPanel infoGrid = new JPanel(new GridLayout(5, 2, 8, 12));
        infoGrid.setOpaque(false);
        infoGrid.setBorder(new EmptyBorder(10, 0, 10, 0));

        nameValue = buildValueLabel();
        studentIdValue = buildValueLabel();
        classNameValue = buildValueLabel();
        majorValue = buildValueLabel();
        reportCountValue = buildValueLabel();

        infoGrid.add(buildKeyLabel("姓名"));
        infoGrid.add(nameValue);
        infoGrid.add(buildKeyLabel("学号"));
        infoGrid.add(studentIdValue);
        infoGrid.add(buildKeyLabel("班级"));
        infoGrid.add(classNameValue);
        infoGrid.add(buildKeyLabel("专业"));
        infoGrid.add(majorValue);
        infoGrid.add(buildKeyLabel("实验报告数"));
        infoGrid.add(reportCountValue);

        JLabel hintLabel = new JLabel("提示：双击学生条目可进入其专栏查看实验报告");
        hintLabel.setForeground(new Color(120, 130, 150));
        hintLabel.setFont(hintLabel.getFont().deriveFont(12f));
        hintLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        detailPanel.add(sectionTitle, BorderLayout.NORTH);
        detailPanel.add(infoGrid, BorderLayout.CENTER);
        detailPanel.add(hintLabel, BorderLayout.SOUTH);

        updateDetails(null);

        return detailPanel;
    }

    private JLabel buildKeyLabel(String text) {
        JLabel label = new JLabel(text + "：");
        label.setForeground(new Color(120, 130, 150));
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        return label;
    }

    private JLabel buildValueLabel() {
        JLabel label = new JLabel("-");
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 14f));
        return label;
    }

    private JPanel buildActions() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnPanel.setOpaque(false);

        JButton addBtn = createPrimaryButton("添加学生");
        JButton editBtn = createSecondaryButton("修改学生");
        JButton delBtn = createDangerButton("删除学生");
        JButton detailBtn = createSecondaryButton("进入专栏");
        JButton resetBtn = createGhostButton("重置列表");

        addBtn.addActionListener(e -> addStudent());
        editBtn.addActionListener(e -> editStudent());
        delBtn.addActionListener(e -> deleteStudent());
        detailBtn.addActionListener(e -> openStudentDetail());
        resetBtn.addActionListener(e -> {
            searchField.setText("");
            loadList();
        });

        btnPanel.add(resetBtn);
        btnPanel.add(detailBtn);
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

    private void updateDetails(Student student) {
        if (student == null) {
            nameValue.setText("请选择学生");
            studentIdValue.setText("-");
            classNameValue.setText("-");
            majorValue.setText("-");
            reportCountValue.setText("-");
            return;
        }
        nameValue.setText(student.getName());
        studentIdValue.setText(student.getStudentId());
        classNameValue.setText(student.getClassName());
        majorValue.setText(student.getMajor());
        reportCountValue.setText(String.valueOf(student.getReports().size()));
    }

    private void loadList() {
        listModel.clear();
        List<Student> all = manager.getAllStudents();
        for (Student s : all) {
            listModel.addElement(s);
        }
        if (!all.isEmpty()) {
            studentListUI.setSelectedIndex(0);
        } else {
            updateDetails(null);
        }
    }

    private void addStudent() {
        StudentForm form = new StudentForm(this, "添加学生");
        Student s = form.showDialog();
        if (s != null) {
            manager.addStudent(s);
            loadList();
        }
    }

    private void deleteStudent() {
        Student s = studentListUI.getSelectedValue();
        if (s != null) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "确定要删除该学生吗？这将同时删除该学生的所有实验报告。",
                    "删除确认",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (option == JOptionPane.YES_OPTION) {
                manager.deleteStudent(s);
                loadList();
            }
        }
    }

    private void editStudent() {
        Student oldS = studentListUI.getSelectedValue();
        if (oldS == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个学生", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StudentForm form = new StudentForm(this, "修改学生信息");
        form.fillData(oldS);
        Student newS = form.showDialog();
        if (newS != null) {
            manager.updateStudent(oldS, newS);
            loadList();
        }
    }

    private void openStudentDetail() {
        Student s = studentListUI.getSelectedValue();
        if (s == null) {
            JOptionPane.showMessageDialog(this, "请先选择一个学生", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        new StudentDetailDialog(this, s, manager).setVisible(true);
        loadList();
    }

    private void searchStudent() {
        String key = searchField.getText() != null ? searchField.getText().trim() : "";
        if (key.isEmpty()) {
            loadList();
            return;
        }

        List<Student> result = manager.searchStudents(key);
        listModel.clear();
        for (Student s : result) {
            listModel.addElement(s);
        }

        if (result.isEmpty()) {
            updateDetails(null);
            JOptionPane.showMessageDialog(this, "没有找到相关学生", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            studentListUI.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }

    private static class StudentCellRenderer extends JPanel implements ListCellRenderer<Student> {

        private final JLabel nameLabel = new JLabel();
        private final JLabel metaLabel = new JLabel();
        private final JLabel reportLabel = new JLabel();

        public StudentCellRenderer() {
            setLayout(new BorderLayout(8, 6));
            setBorder(new EmptyBorder(12, 16, 12, 16));
            setOpaque(true);

            nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 15f));

            metaLabel.setFont(metaLabel.getFont().deriveFont(12f));
            metaLabel.setForeground(new Color(120, 130, 150));

            reportLabel.setFont(reportLabel.getFont().deriveFont(11f));
            reportLabel.setForeground(new Color(64, 99, 255));

            add(nameLabel, BorderLayout.NORTH);
            add(metaLabel, BorderLayout.CENTER);
            add(reportLabel, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Student> list, Student value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                nameLabel.setText(safe(value.getName()));
                metaLabel.setText(String.format("学号：%s · 班级：%s · 专业：%s",
                        safe(value.getStudentId()),
                        safe(value.getClassName()),
                        safe(value.getMajor())));
                reportLabel.setText("实验报告：" + value.getReports().size() + " 份");
            }

            if (isSelected) {
                setBackground(new Color(64, 99, 255, 30));
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(64, 99, 255, 120), 1, true),
                        new EmptyBorder(12, 16, 12, 16)
                ));
            } else {
                setBackground(Color.WHITE);
                setBorder(new EmptyBorder(12, 16, 12, 16));
            }
            return this;
        }

        private String safe(String text) {
            return (text == null || text.trim().isEmpty()) ? "-" : text.trim();
        }
    }
}
