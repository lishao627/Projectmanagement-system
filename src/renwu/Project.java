package renwu;

import java.io.Serializable;

public class Project implements Serializable {

    private String name;
    private String type;
    private String author;
    private String date;
    private String description;

    public Project(String name, String type, String author, String date, String description) {
        this.name = name;
        this.type = type;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    // Getter 方法
    public String getName() { return name; }
    public String getType() { return type; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getDescription() { return description; }

    // 让 JList 显示项目名称
    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}
