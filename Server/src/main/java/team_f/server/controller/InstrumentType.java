package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.InstrumentTypeApplication;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.entities.list.InstrumentTypeList;
import team_f.jsonconnector.entities.special.request.EventDutyRequest;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.InstrumentTypeConverter;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = {URIList.instrumentType})
public class InstrumentType extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            EventDutyRequest request = (EventDutyRequest) ReadHelper.readJSONObject(req.getReader(), EventDutyRequest.class);

            if(request != null) {
                InstrumentTypeApplication facade = new InstrumentTypeApplication();
                team_f.jsonconnector.entities.InstrumentType instrumentType;
                javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;
                ErrorList errorList = null;

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        // @TODO: implement
                        break;
                    case GET_ALL:
                        List<team_f.domain.entities.InstrumentType> instrumentTypeEntityList = facade.getInstrumentTypeList();
                        List<team_f.jsonconnector.entities.InstrumentType> instrumentTypeList = new LinkedList<>();
                        InstrumentTypeList instrumentTypes = new InstrumentTypeList();

                        for(team_f.domain.entities.InstrumentType item : instrumentTypeEntityList) {
                            instrumentType = InstrumentTypeConverter.convertToJSON(item);
                            instrumentTypeList.add(instrumentType);
                        }

                        instrumentTypes.setEventDutyList(instrumentTypeList);

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), instrumentTypes);

                        break;
                    case CREATE:
                        // @TODO: implement
                        break;
                    case UPDATE:
                        // @TODO: implement
                        break;
                    case DELETE:
                        // @TODO: implement
                        break;
                    default:
                        break;
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}