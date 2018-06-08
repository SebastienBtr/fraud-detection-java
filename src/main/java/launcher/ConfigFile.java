package launcher;


import java.util.HashMap;
import java.util.Map;

/**
 * Allows user to choose parameters for the comparison styles
 */
public class ConfigFile {

    public static boolean classNameAreGiven = true;

    public static boolean methodNamesAreGiven = true;

    public static Map<String,Boolean> indicators = new HashMap<String, Boolean>()
    {{
        put("Moyenne", true);
        put("Structure Algoritmique", true);
        put("autre", false);
    }};

}
