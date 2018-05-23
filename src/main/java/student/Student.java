package student;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Student {
    @Getter @Setter private String name;
    @Getter @Setter private List<Class> files;

    public Student(String name) {
        this.name = name;
        this.files = new ArrayList<Class>();
    }
}
