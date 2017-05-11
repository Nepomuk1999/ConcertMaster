package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Account;
import team_f.domain.enums.AccountProperty;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

public class AccountLogic implements EntityLogic<Account, AccountProperty> {
    protected AccountLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(Account account, AccountProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (AccountProperty property : properties) {

            switch (property) {
                case USERNAME:
                    if (account.getUsername() == null) {
                        resultList.add(new Pair<>(String.valueOf(property.USERNAME), "is empty"));
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
        List<Pair<String, String>> result = new LinkedList<>();

        return validate(account, AccountProperty.values());
    }
}
