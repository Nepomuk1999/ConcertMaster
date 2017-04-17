package team_f.application.helper;

import javafx.util.Pair;
import team_f.application.Application;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import java.util.List;

public class DomainEntityHelper {
    public static List<MusicalWork> getMusicalWorkList() {
        Application facade = new Application();
        List<MusicalWork> musicalWorkList = facade.getMusicalWorkList();

        return musicalWorkList;
    }

    public static List<Instrumentation> getInstrumentationList() {
        Application facade = new Application();
        List<Instrumentation> instrumentationList = facade.getInstrumentationList();

        return instrumentationList;
    }

    public static List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        Application facade = new Application();
        List<Pair<MusicalWork, Instrumentation>> musicalWorkInstrumentation = facade.getMusicalWorkInstrumentationList();

        return musicalWorkInstrumentation;
    }
}
