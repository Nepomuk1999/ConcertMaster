package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Account;
import team_f.domain.enums.properties.AccountProperty;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.properties.AccountProperty.*;

public class AccountLogic implements EntityLogic<Account, AccountProperty> {
    protected AccountLogic() {
    }

    /** Function to return a resultList that shows that the specific username of an account is empty if it is
     *
     * @param account
     * @param properties
     * @return      resultList      returns         returns list of Pair<String, String>
     */
    @Override
    public List<Pair<String, String>> validate(Account account, AccountProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (AccountProperty property : properties) {

            switch (property) {
                case USERNAME:
                    if (account.getUsername() == null) {
                        resultList.add(new Pair<>(String.valueOf(USERNAME), "is empty"));
                    }

                    break;

                // @TODO: validate the cases
                // use AccountLogic for the account specific logic
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Account account) {
        return validate(account, AccountProperty.values());
    }
}
