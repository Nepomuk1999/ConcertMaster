package team_f.domain.helper;

public class TextHelper {
    public static String getSeparatedText(char separator, int ... values) {
        StringBuilder builder = new StringBuilder();

        if(values != null) {
            for(int i = 0; i < values.length; i++) {
                builder.append(values[i]);

                if(i +1 < values.length) {
                    builder.append(separator);
                }
            }
        }

        return builder.toString();
    }
    public static String getTextFromString(char separator, String ... instruments) {
        StringBuilder builder = new StringBuilder();

        if(instruments != null) {
            for(int i = 0; i < instruments.length; i++) {
                builder.append(instruments[i]);

                if(i +1 < instruments.length) {
                    builder.append(separator);
                }
            }
        }

        return builder.toString();
    }
}
