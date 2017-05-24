package UseCases.MusicalWork;

import Database.Facade.DatabaseFacade;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
public class MusicalWorkController {
    private final DatabaseFacade facade;

    public MusicalWorkController() {
        facade = new DatabaseFacade();
    }

    private List<MusicalWorkDomainInterface> getAllMusicalWorks() {
        return facade.getAllMusicalWorks();
    }

    public List<MusicalWorkViewInterface> getAllMusicalWorksAsView() {
        List<MusicalWorkViewInterface> musicalWorks = new LinkedList<>();
        musicalWorks.addAll(getAllMusicalWorks());
        return musicalWorks;
    }
}
