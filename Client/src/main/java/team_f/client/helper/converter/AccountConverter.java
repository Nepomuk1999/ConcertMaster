package team_f.client.helper.converter;

import team_f.jsonconnector.entities.Account;
import team_f.jsonconnector.enums.AccountRole;

public class AccountConverter {
    public static Account convertToJSON(team_f.domain.entities.Account account) {
        Account result = new Account();
        result.setAccountID(account.getAccountID());
        result.setUsername(account.getUsername());
        result.setPassword(account.getPassword());
        result.setRole(AccountRole.valueOf(String.valueOf(account.getRole())));

        return result;
    }
}
