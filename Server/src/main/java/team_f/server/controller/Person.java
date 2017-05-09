package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.PersonApplication;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.enums.*;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
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

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        break;
                    case GET_ALL:
                        List<team_f.domain.entities.Person> personEntityList = facade.getAllMusicians();
                        List<team_f.jsonconnector.entities.Person> personList = new LinkedList<>();
                        PersonList persons = new PersonList();
                        team_f.jsonconnector.entities.Person person;
                        Instrument instrument;
                        Account account;

                        for(team_f.domain.entities.Person item : personEntityList) {
                            person = new team_f.jsonconnector.entities.Person();
                            person.setPersonID(item.getPersonID());
                            person.setFirstname(item.getFirstname());
                            person.setLastname(item.getLastname());
                            person.setAddress(item.getAddress());
                            person.setEmail(item.getEmail());

                            if(item.getGender().equals("m")) {
                                person.setGender(Gender.MALE);
                            } else if(item.getGender().equals("w")){
                                person.setGender(Gender.FEMALE);
                            }

                            person.setInitials(item.getInitials());
                            person.setPersonRole(PersonRole.valueOf(String.valueOf(item.getPersonRole())));
                            person.setPhoneNumber(item.getPhoneNumber());

                            if(item.getInstruments() != null) {
                                // @TODO: only the first perhaps this feature can be improved in the future
                                for(int i = 0; i < item.getInstruments().size() && i < 1; i++) {
                                    instrument = new Instrument();
                                    instrument.setInstrumentID(item.getInstruments().get(i).getInstrumentID());
                                    instrument.setBrand(item.getInstruments().get(i).getBrand());
                                    instrument.setModel(item.getInstruments().get(i).getModel());
                                    instrument.setInstrumentType(InstrumentType.valueOf(String.valueOf(item.getInstruments().get(i).getInstrumentType())));
                                }
                            }

                            if(item.getAccount() != null) {
                                account = new Account();
                                account.setAccountID(item.getAccount().getAccountID());
                                account.setPassword(item.getAccount().getPassword());
                                account.setRole(AccountRole.valueOf(String.valueOf(item.getAccount().getRole())));
                                account.setUsername(item.getAccount().getUsername());

                                person.setAccount(account);
                            }

                            personList.add(person);
                        }

                        persons.setPersonList(personList);

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), persons);

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