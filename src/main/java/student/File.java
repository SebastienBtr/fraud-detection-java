package student;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class File {

    @Getter
    @Setter
    private List<Variable> attributes;



    @Getter
    @Setter
    private String filePath;

    public File() {
        this.attributes = new ArrayList<Variable>();

    }
}
