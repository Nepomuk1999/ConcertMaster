package team_f.application.helper;

import javafx.util.Pair;
import team_f.application.EventApplication;
import team_f.application.InstrumentationApplication;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;

import java.util.List;

public class DomainEntityHelper {
    public static List<MusicalWork> getMusicalWorkList() {
        EventApplication facade = new EventApplication();
        List<MusicalWork> musicalWorkList = facade.getMusicalWorkList();

        return musicalWorkList;
    }

    public static List<Instrumentation> getInstrumentationList() {
        InstrumentationApplication facade = new InstrumentationApplication();
        List<Instrumentation> instrumentationList = facade.getInstrumentationList();

        return instrumentationList;
    }

    public static List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        EventApplication facade = new EventApplication();
        List<Pair<MusicalWork, Instrumentation>> musicalWorkInstrumentation = facade.getMusicalWorkInstrumentationList();

        return musicalWorkInstrumentation;
    }
}
