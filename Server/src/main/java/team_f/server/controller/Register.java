package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.PersonApplication;
import team_f.domain.entities.Person;
import team_f.domain.enums.AccountRole;
import team_f.domain.enums.InstrumentType;
import team_f.domain.enums.PersonRole;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.list.ErrorList;
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
import java.util.List;

@WebServlet(urlPatterns = {URIList.register})
public class Register extends HttpServlet {
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
                Pair<DomainEntity, List<Pair<String, String>>> tmpErrorList;

                team_f.jsonconnector.entities.Person person = request.getEntity();
                ErrorList errorList = null;
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

                    List<InstrumentType> instrumentTypeList = new ArrayList<>();

                    if(person.getInstrumentTypeList() != null) {
                        for(team_f.jsonconnector.enums.InstrumentType instrumentType : person.getInstrumentTypeList()) {
                            try {
                                instrumentTypeList.add(InstrumentType.valueOf(String.valueOf(instrumentType)));
                            } catch (Exception e) {
                            }
                        }
                    }

                    // @TODO: should we use enums for gender?
                    tmpErrorList = facade.add(0, person.getFirstname(), person.getLastname(), String.valueOf(person.getGender()), person.getAddress(), person.getEmail(),
                            person.getPhoneNumber(), 0, personRole, username, accountRole, instrumentTypeList);

                    errorList = JsonResponse.prepareErrorMessage(PersonConverter.convertToJSON((Person) tmpErrorList.getKey()), tmpErrorList.getValue());
                }

                resp.setContentType(MediaType.APPLICATION_JSON);
                WriteHelper.writeJSONObject(resp.getWriter(), errorList);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
