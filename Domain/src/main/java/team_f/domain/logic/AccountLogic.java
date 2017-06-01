package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Account;
import team_f.domain.enums.AccountRole;
import team_f.domain.enums.properties.AccountProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.properties.AccountProperty.*;
import static team_f.domain.enums.properties.EventDutyProperty.ID;

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
                case ID:
                    if (!IntegerHelper.isValidId(account.getAccountID())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }

                    break;
                case USERNAME:
                    if (account.getUsername() == null && !StringHelper.isNotEmpty(account.getUsername())) {
                        resultList.add(new Pair<>(String.valueOf(USERNAME), "is empty"));
                    }

                    break;
                case PASSWORD:
                    if (account.getPassword() == null && !StringHelper.isNotEmpty(account.getPassword())) {
                        resultList.add(new Pair<>(String.valueOf(PASSWORD), "is empty"));
                    }

                    break;
                case ACCOUNT_ROLE:
                    if (account.getRole() == null) {
                        resultList.add(new Pair<>(String.valueOf(ACCOUNT_ROLE), "is empty"));
                    } else {
                        boolean isValid = false;
                        for (AccountRole accountRole : AccountRole.values()) {
                            if (String.valueOf(accountRole).equals(String.valueOf(account.getRole()))) {
                                isValid = true;
                            }
                        }

                        if (!isValid) {
                            resultList.add(new Pair<>(String.valueOf(ACCOUNT_ROLE), "is not valid"));
                        }
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Account account) {
        return validate(account, AccountProperty.values());
    }
}
