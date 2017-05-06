package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.PersonApplication;
import team_f.domain.enums.AccountRole;
import team_f.domain.enums.PersonRole;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {URIList.register})
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
            team_f.jsonconnector.entities.Person person = (team_f.jsonconnector.entities.Person) ReadHelper.readJSONObject(req.getReader(), team_f.jsonconnector.entities.Person.class);

            if(person != null) {
                PersonApplication facade = new PersonApplication();
                Pair<DomainEntity, List<Pair<String, String>>> tmpErrorList;

                // @TODO: instrumentation?
                PersonRole personRole = null;
                String username = null;
                AccountRole accountRole = null;

                try {
                    personRole = PersonRole.valueOf(String.valueOf(person.getPersonRole()));
                } catch (Exception e) {
                }

                if(person.getAccount() != null) {
                    username = person.getAccount().getUsername();
                    accountRole = AccountRole.valueOf(String.valueOf(person.getAccount().getRole()));
                }

                // @TODO: should we use enums for gender?
                // @TODO: instruments?
                tmpErrorList = facade.register(person.getFirstname(), person.getLastname(), String.valueOf(person.getGender()), person.getAddress(), person.getEmail(),
                                               person.getPhoneNumber(), personRole, username, accountRole, null);

                ErrorList errorList = JsonResponse.prepareErrorMessage(tmpErrorList.getKey(), tmpErrorList.getValue());
                resp.setContentType(MediaType.APPLICATION_JSON);
                WriteHelper.writeJSONObject(resp.getWriter(), errorList);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
