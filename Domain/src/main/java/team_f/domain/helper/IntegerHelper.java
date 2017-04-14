package team_f.domain.helper;

public class IntegerHelper {
    public static boolean isValidId(int id) {
        if(id > 0) {
            return true;
        }

        return false;
    }

    public static boolean isPositiveDefaultPoint(int points) {
        if(points >= 0) {
            return true;
        }

        return false;
    }
}
