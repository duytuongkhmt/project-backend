package project.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    public static String normalize(String input) {
        if (input == null) {
            return null;
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }
}
