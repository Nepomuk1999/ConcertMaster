package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Request;
import team_f.domain.enums.properties.RequestProperty;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;

import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.properties.RequestProperty.*;

public class RequestLogic implements EntityLogic<Request, RequestProperty> {
    protected RequestLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(Request request, RequestProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (RequestProperty property : properties) {

            switch (property) {
                case DESCRIPTION:
                    if (request.getDescription() == null && !StringHelper.isNotEmpty(request.getDescription())) {
                        resultList.add(new Pair<>(String.valueOf(DESCRIPTION), "is empty"));
                    }

                    break;
                case EVENT_DUTY:
                    if (request.getEventDuty() == null) {
                        resultList.add(new Pair<>(String.valueOf(EVENT_DUTY), "is empty"));
                    }

                    break;
                case REQUEST_TYPE:
                    if (request.getRequestType() == null) {
                        resultList.add(new Pair<>(String.valueOf(REQUEST_TYPE), "is empty"));
                    }

                    break;
                case PERSON:
                    if (request.getPerson() == null) {
                        resultList.add(new Pair<>(String.valueOf(PERSON), "is empty"));
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Request request) {
        return validate(request, RequestProperty.values());
    }
}