package team_f.client.singletons;

import team_f.application.MusicalWorkApplication;
import team_f.client.configuration.Configuration;
import team_f.client.helper.converter.InstrumentationConverter;
import team_f.client.helper.converter.MusicalWorkConverter;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicalwork.MusicalWorkManagement;
import team_f.client.pages.musicalwork.MusicalWorkParameter;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.MusicalWorkErrorList;
import java.util.LinkedList;
import java.util.List;

public class MusicalWorkSingleton {
    private static MusicalWorkManagement _musicalWork;
    private static MusicalWorkApplication _facade = MusicalWorkApplication.getInstance();
    private static Configuration _configuration;

    private MusicalWorkSingleton() {
    }

    public static MusicalWorkManagement getInstance(Configuration configuration) {
        if (_musicalWork == null) {
            _configuration = configuration;
            _musicalWork = new MusicalWorkManagement();

            _musicalWork.setOnLoadList(new PageAction<List<MusicalWork>, MusicalWorkParameter>() {
                @Override
                public List<MusicalWork> doAction(MusicalWorkParameter value) {
                    if(value != null) {
                        List<team_f.domain.entities.MusicalWork> musicalWorkEntityList = _facade.getList();
                        List<team_f.jsonconnector.entities.MusicalWork> musicalWorkList = new LinkedList<>();
                        MusicalWork musicalWork;

                        for(team_f.domain.entities.MusicalWork item : musicalWorkEntityList) {
                            musicalWork = MusicalWorkConverter.convertToJSON(item);
                            musicalWorkList.add(musicalWork);
                        }

                        return musicalWorkList;
                    }

                    return null;
                }
            });

            _musicalWork.setOnCreate(new PageAction<MusicalWorkErrorList, MusicalWork>() {
                @Override
                public MusicalWorkErrorList doAction(MusicalWork value) {
                    if(value != null) {
                        return addEdit(value);
                    }

                    return null;
                }
            });

            _musicalWork.setOnDelete(new PageAction<MusicalWorkErrorList, MusicalWork>() {
                @Override
                public MusicalWorkErrorList doAction(MusicalWork value) {
                    if(value != null) {
                        // @TODO: add delete functionality
                        return new MusicalWorkErrorList();
                    }

                    return null;
                }
            });

            _musicalWork.setOnEdit(new PageAction<MusicalWorkErrorList, MusicalWork>() {
                @Override
                public MusicalWorkErrorList doAction(MusicalWork value) {
                    if(value != null) {
                        return addEdit(value);
                    }

                    return null;
                }
            });
        }

        return _musicalWork;
    }

    private static MusicalWorkErrorList addEdit(MusicalWork musicalWork) {
        List<team_f.application.entities.SpecialInstrumentation> specialInstrumentationList = new LinkedList<>();
        team_f.application.entities.SpecialInstrumentation specialInstrumentation;
        javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;

        if(musicalWork.getInstrumentation() != null && musicalWork.getInstrumentation().getSpecialInstrumentation() != null) {
            for(SpecialInstrumentation item : musicalWork.getInstrumentation().getSpecialInstrumentation()) {
                specialInstrumentation = new team_f.application.entities.SpecialInstrumentation();
                specialInstrumentation.setID(item.getSpecialInstrumentationID());
                specialInstrumentation.setSpecialInstrumentation(item.getSpecialInstrumentation());
                specialInstrumentation.setSectionType(item.getSectionType());
                specialInstrumentation.setSpecialInstrumentationCount(item.getSpecialInstrumentCount());

                specialInstrumentationList.add(specialInstrumentation);
            }
        }

        tmpErrorList = _facade.addMusicalWork(0, musicalWork.getName(), musicalWork.getComposer(), musicalWork.getInstrumentation().getViolin1(), musicalWork.getInstrumentation().getViolin2(),
                musicalWork.getInstrumentation().getViola(), musicalWork.getInstrumentation().getViolincello(), musicalWork.getInstrumentation().getDoublebass(), musicalWork.getInstrumentation().getFlute(),
                musicalWork.getInstrumentation().getOboe(), musicalWork.getInstrumentation().getClarinet(), musicalWork.getInstrumentation().getBassoon(), musicalWork.getInstrumentation().getHorn(),
                musicalWork.getInstrumentation().getTrumpet(), musicalWork.getInstrumentation().getTrombone(), musicalWork.getInstrumentation().getTube(), musicalWork.getInstrumentation().getKettledrum(),
                musicalWork.getInstrumentation().getPercussion(), musicalWork.getInstrumentation().getHarp(), specialInstrumentationList);

        MusicalWorkErrorList musicalWorkErrorList = new MusicalWorkErrorList();
        Error error;
        List<Error> errors = new LinkedList<>();

        if(tmpErrorList != null) {
            for(javafx.util.Pair<String, String> item : tmpErrorList.getValue()) {
                error = new Error();
                error.setKey(item.getKey());
                error.setValue(item.getValue());

                errors.add(error);
            }
        }

        List<team_f.jsonconnector.entities.Pair<MusicalWork, List<Error>>> list = new LinkedList<>();
        team_f.jsonconnector.entities.Pair pair = new team_f.jsonconnector.entities.Pair();
        pair.setKey(MusicalWorkConverter.convertToJSON((team_f.domain.entities.MusicalWork) tmpErrorList.getKey()));
        pair.setValue(errors);
        list.add(pair);
        musicalWorkErrorList.setErrorList(list);

        return musicalWorkErrorList;
    }
}
