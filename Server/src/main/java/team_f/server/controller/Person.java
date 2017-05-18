package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.PersonApplication;
import team_f.domain.enums.AccountRole;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.enums.PersonRole;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.entities.list.PersonList;
import team_f.jsonconnector.entities.special.request.PersonRequest;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.PersonConverter;
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
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
            PersonRequest request = (PersonRequest) ReadHelper.readJSONObject(req.getReader(), PersonRequest.class);

            if(request != null) {
                PersonApplication facade = new PersonApplication();
                team_f.jsonconnector.entities.Person person;
                javafx.util.Pair<DomainEntity, List<Pair<String, String>>> tmpErrorList;
                ErrorList errorList = null;

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        // @TODO: add get by parameter functionality
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
                        WriteHelper.writeJSONObject(resp.getWriter(), persons);

                        break;
                    case CREATE:
                        // this is handled by the register servlet
                        break;
                    case UPDATE:
                        person = request.getEntity();
                        PersonRole personRole = null;
                        String username = null;
                        AccountRole accountRole = null;

                        if(person != null) {
                            try {
                                personRole = PersonRole.valueOf(String.valueOf(person.getPersonRole()));
                            } catch (Exception e) {
                            }

                            if(person.getAccount() != null) {
                                username = person.getAccount().getUsername();
                                accountRole = AccountRole.valueOf(String.valueOf(person.getAccount().getRole()));
                            }

                            /*List<Integer> instrumentTypeList = new ArrayList<>();

                            if(person.getInstrumentTypeList() != null) {
                                for(team_f.jsonconnector.entities.InstrumentType instrumentType : person.getInstrumentTypeList()) {
                                    if(instrumentType != null) {
                                        instrumentTypeList.add(instrumentType.getID());
                                    }
                                }
                            }*/

                            List<AllInstrumentTypes> instrumentTypeList = new ArrayList<>();

                            if(person.getInstrumentTypeList() != null) {
                                for(team_f.jsonconnector.enums.InstrumentType instrumentType : person.getInstrumentTypeList()) {
                                    try {
                                        instrumentTypeList.add(AllInstrumentTypes.valueOf(String.valueOf(instrumentType)));
                                    } catch (Exception e) {
                                    }
                                }
                            }

                            tmpErrorList = facade.add(person.getPersonID(), person.getFirstname(), person.getLastname(), String.valueOf(person.getGender()), person.getAddress(), person.getEmail(),
                                    person.getPhoneNumber(), person.getPersonID(), personRole, username, accountRole, instrumentTypeList);

                            errorList = JsonResponse.prepareErrorMessage(PersonConverter.convertToJSON((team_f.domain.entities.Person) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

                        break;
                    case DELETE:
                        person = request.getEntity();

                        if(person != null) {
                            // @TODO: add delete functionality
                            /*tmpErrorList = facade.delete(person.getPersonID());
                            errorList = JsonResponse.prepareErrorMessage(InstrumentationConverter.convertToJSON((team_f.domain.entities.Instrumentation) tmpErrorList.getKey()), tmpErrorList.getValue());*/
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