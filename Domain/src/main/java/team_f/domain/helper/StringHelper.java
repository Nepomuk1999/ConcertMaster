package team_f.domain.helper;

import java.util.List;

public class StringHelper {
    public static String check(String text, int min, int max) {
        String result = "";

        if(text != null) {
            if(text.length() < min) {
                result = "has to be at least " + min + "long";
            } else if (text.length() <= max) {
                result = "and at most" + max + "long";
            }
        }

        return result;
    }
}
