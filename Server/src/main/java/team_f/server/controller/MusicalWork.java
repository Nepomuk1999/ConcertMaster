package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.MusicalWorkApplication;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.SpecialInstrumentation;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.entities.list.MusicalWorkList;
import team_f.jsonconnector.entities.special.request.MusicalWorkRequest;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.MusicalWorkConverter;
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = {URIList.musicalWork})
public class MusicalWork extends HttpServlet {
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
            MusicalWorkRequest request = (MusicalWorkRequest) ReadHelper.readJSONObject(req.getReader(), MusicalWorkRequest.class);

            if(request != null) {
                MusicalWorkApplication facade = MusicalWorkApplication.getInstance();
                team_f.jsonconnector.entities.MusicalWork musicalWork;
                javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;
                ErrorList errorList = null;

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        // @TODO: add get by parameter functionality
                        break;
                    case GET_ALL:
                        List<team_f.domain.entities.MusicalWork> musicalWorkEntityList = facade.getList();
                        List<team_f.jsonconnector.entities.MusicalWork> musicalWorkList = new LinkedList<>();
                        MusicalWorkList musicalWorks = new MusicalWorkList();

                        for(team_f.domain.entities.MusicalWork item : musicalWorkEntityList) {
                            musicalWork = MusicalWorkConverter.convertToJSON(item);
                            musicalWorkList.add(musicalWork);
                        }

                        musicalWorks.setMusicalWorkList(musicalWorkList);

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), musicalWorks);

                        break;
                    case CREATE:
                    case UPDATE:
                        musicalWork = request.getEntity();

                        if(musicalWork != null) {
                            List<team_f.application.entities.SpecialInstrumentation> specialInstrumentationList = new LinkedList<>();
                            team_f.application.entities.SpecialInstrumentation specialInstrumentation;

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

                             tmpErrorList = facade.addMusicalWork(0, musicalWork.getName(), musicalWork.getComposer(), musicalWork.getInstrumentation().getViolin1(), musicalWork.getInstrumentation().getViolin2(),
                                    musicalWork.getInstrumentation().getViola(), musicalWork.getInstrumentation().getViolincello(), musicalWork.getInstrumentation().getDoublebass(), musicalWork.getInstrumentation().getFlute(),
                                    musicalWork.getInstrumentation().getOboe(), musicalWork.getInstrumentation().getClarinet(), musicalWork.getInstrumentation().getBassoon(), musicalWork.getInstrumentation().getHorn(),
                                    musicalWork.getInstrumentation().getTrumpet(), musicalWork.getInstrumentation().getTrombone(), musicalWork.getInstrumentation().getTube(), musicalWork.getInstrumentation().getKettledrum(),
                                    musicalWork.getInstrumentation().getPercussion(), musicalWork.getInstrumentation().getHarp(), specialInstrumentationList);

                            errorList = JsonResponse.prepareErrorMessage(MusicalWorkConverter.convertToJSON((team_f.domain.entities.MusicalWork) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

                        break;
                    case DELETE:
                        musicalWork = request.getEntity();

                        if(musicalWork != null) {
                            // @TODO: add delete functionality
                            /*tmpErrorList = facade.delete(musicalWork.getMusicalWorkID());
                            errorList = JsonResponse.prepareErrorMessage(MusicalWorkConverter.convertToJSON((team_f.domain.entities.MusicalWork) tmpErrorList.getKey()), tmpErrorList.getValue());*/
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

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