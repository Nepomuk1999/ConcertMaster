package GUI.Event;


import jfxtras.internal.scene.control.skin.agenda.base24hour.AgendaSkinTimeScale24HourAbstract;
import jfxtras.scene.control.agenda.Agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian
 */
class MyMonthSkin extends AgendaSkinTimeScale24HourAbstract<MyMonthSkin> {


    MyMonthSkin(Agenda control) {
        super(control);
    }

    /**
     * Assign a calendar to each day, so it knows what it must draw.
     */
    protected List<LocalDate> determineDisplayedLocalDates() {
        List<LocalDate> lLocalDates = new ArrayList<>();
        LocalDate lStartLocalDate = getFirstDayOfMonthLocalDate();
        for (int i = 0; i < lStartLocalDate.lengthOfMonth(); i++) {
            lLocalDates.add(lStartLocalDate.plusDays(i));
        }
        return lLocalDates;
    }

    private LocalDate getFirstDayOfMonthLocalDate() {
        long dayOfMonth = control.getDisplayedLocalDateTime().getDayOfMonth() - 1;
        LocalDateTime dateTime = control.getDisplayedLocalDateTime().minusDays(dayOfMonth);
        return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
    }
}