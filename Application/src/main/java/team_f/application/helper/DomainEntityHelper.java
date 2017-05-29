package team_f.application.helper;

import javafx.util.Pair;
import team_f.application.EventApplication;
import team_f.application.InstrumentationApplication;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;

import java.util.List;

public class DomainEntityHelper {
    public static List<MusicalWork> getMusicalWorkList() {
        EventApplication facade = EventApplication.getInstance();
        List<MusicalWork> musicalWorkList = facade.getMusicalWorkList();

        return musicalWorkList;
    }

    public static List<Instrumentation> getInstrumentationList() {
        InstrumentationApplication facade = InstrumentationApplication.getInstance();
        List<Instrumentation> instrumentationList = facade.getInstrumentationList();

        return instrumentationList;
    }

    public static List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        EventApplication facade = EventApplication.getInstance();
        List<Pair<MusicalWork, Instrumentation>> musicalWorkInstrumentation = facade.getMusicalWorkInstrumentationList();

        return musicalWorkInstrumentation;
    }
}
