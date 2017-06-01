package team_f.client.singletons;

import javafx.util.Pair;
import team_f.application.InstrumentationApplication;
import team_f.client.configuration.Configuration;
import team_f.client.helper.converter.InstrumentationConverter;
import team_f.client.pages.PageAction;
import team_f.client.pages.instrumentationmanagement.InstrumentationManagement;
import team_f.client.pages.instrumentationmanagement.InstrumentationParameter;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.SpecialInstrumentation;
import team_f.jsonconnector.entities.special.errorlist.InstrumentationErrorList;
import java.util.LinkedList;
import java.util.List;

public class InstrumentationSingleton {
    private static InstrumentationManagement _instrumentation;
    private static InstrumentationApplication _facade = InstrumentationApplication.getInstance();
    private static Configuration _configuration;

    private InstrumentationSingleton(){
    }

    public static InstrumentationManagement getInstance(Configuration configuration){
        if(_instrumentation == null) {
            _configuration = configuration;
            _instrumentation = new InstrumentationManagement();

            _instrumentation.setOnLoadList(new PageAction<List<Instrumentation>, InstrumentationParameter>() {
                @Override
                public List<Instrumentation> doAction(InstrumentationParameter value) {
                    if(value != null) {
                        List<team_f.domain.entities.Instrumentation> instrumentationEntityList = _facade.getList();
                        List<team_f.jsonconnector.entities.Instrumentation> instrumentationList = new LinkedList<>();
                        Instrumentation instrumentation;

                        for(team_f.domain.entities.Instrumentation item : instrumentationEntityList) {
                            instrumentation = InstrumentationConverter.convertToJSON(item);
                            instrumentationList.add(instrumentation);
                        }

                        return instrumentationList;
                    }

                    return null;
                }
            });
            _instrumentation.setOnCreate(new PageAction<InstrumentationErrorList, Instrumentation>() {
                @Override
                public InstrumentationErrorList doAction(Instrumentation value) {
                    return addEdit(value);
                }
            });

            _instrumentation.setOnDelete(new PageAction<InstrumentationErrorList, Instrumentation>() {
                @Override
                public InstrumentationErrorList doAction(Instrumentation value) {
                    // @TODO: add delete functionality
                    InstrumentationErrorList errorList = new InstrumentationErrorList();
                    return errorList;
                }
            });

            _instrumentation.setOnEdit(new PageAction<InstrumentationErrorList, Instrumentation>() {
                @Override
                public InstrumentationErrorList doAction(Instrumentation value) {
                    return addEdit(value);
                }
            });
        }
        return _instrumentation;
    }

    private static InstrumentationErrorList addEdit(Instrumentation instrumentation) {
        List<team_f.application.entities.SpecialInstrumentation> specialInstrumentationList = new LinkedList<>();
        team_f.application.entities.SpecialInstrumentation specialInstrumentation;
        javafx.util.Pair<DomainEntity, List<Pair<String, String>>> tmpErrorList;

        if(instrumentation.getSpecialInstrumentation() != null) {
            for(SpecialInstrumentation item : instrumentation.getSpecialInstrumentation()) {
                specialInstrumentation = new team_f.application.entities.SpecialInstrumentation();
                specialInstrumentation.setID(item.getSpecialInstrumentationID());
                specialInstrumentation.setSpecialInstrumentation(item.getSpecialInstrumentation());
                specialInstrumentation.setSectionType(item.getSectionType());
                specialInstrumentation.setSpecialInstrumentationCount(item.getSpecialInstrumentCount());

                specialInstrumentationList.add(specialInstrumentation);
            }
        }

        tmpErrorList = _facade.addInstrumentation(0, instrumentation.getViolin1(), instrumentation.getViolin2(), instrumentation.getViola(), instrumentation.getViolincello(),
                instrumentation.getDoublebass(), instrumentation.getFlute(), instrumentation.getOboe(), instrumentation.getClarinet(), instrumentation.getBassoon(), instrumentation.getHorn(), instrumentation.getTrumpet(),
                instrumentation.getTrombone(), instrumentation.getTube(),instrumentation.getKettledrum(), instrumentation.getPercussion(), instrumentation.getHarp(),
                specialInstrumentationList);

        InstrumentationErrorList instrumentationErrorList = new InstrumentationErrorList();
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

        List<team_f.jsonconnector.entities.Pair<Instrumentation, List<Error>>> list = new LinkedList<>();
        team_f.jsonconnector.entities.Pair pair = new team_f.jsonconnector.entities.Pair();
        pair.setKey(InstrumentationConverter.convertToJSON((team_f.domain.entities.Instrumentation) tmpErrorList.getKey()));
        pair.setValue(errors);
        list.add(pair);
        instrumentationErrorList.setErrorList(list);

        return instrumentationErrorList;
    }
}
