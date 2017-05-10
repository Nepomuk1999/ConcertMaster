package team_f.server.controller;

import javafx.util.*;
import org.json.JSONArray;
import team_f.application.PersonApplication;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.PersonConverter;
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

@WebServlet(urlPatterns = {URIList.person})
public class Person extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            team_f.jsonconnector.entities.Request request = (team_f.jsonconnector.entities.Request) ReadHelper.readJSONObject(req.getReader(), team_f.jsonconnector.entities.Request.class);

            if(request != null) {
                PersonApplication facade = new PersonApplication();
                team_f.jsonconnector.entities.Person person;
                javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        break;
                    case GET_ALL:
                        List<team_f.domain.entities.Person> personEntityList = facade.getAllMusicians();
                        List<team_f.jsonconnector.entities.Person> personList = new LinkedList<>();
                        PersonList persons = new PersonList();

                        for(team_f.domain.entities.Person item : personEntityList) {
                            person = PersonConverter.convertToJSON(item);
                            personList.add(person);
                        }

                        persons.setPersonList(personList);

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        resp.setCharacterEncoding("UTF-8");
                        WriteHelper.writeJSONObject(resp.getWriter(), persons);

                        break;
                    case UPDATE:
                        // @TODO: add update functionality
                        /*person = (team_f.jsonconnector.entities.Person) request.getEntity();
                        tmpErrorList = facade.updatePerson(person);
                        person = PersonConverter.convertToJSON((team_f.domain.entities.Person) tmpErrorList.getKey());

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        resp.setCharacterEncoding("UTF-8");
                        WriteHelper.writeJSONObject(resp.getWriter(), person);*/

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