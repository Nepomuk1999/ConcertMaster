package team_f.server.helper;

import javafx.scene.paint.Color;

public class CSSHelper {
    public static String getColor(Color color) {
        if(color != null) {
            return String.format( "#%02X%02X%02X",
                                (int)( color.getRed() * 255),
                                (int)( color.getGreen() * 255),
                                (int)( color.getBlue() * 255 ));
        }

        return "";
    }
}
