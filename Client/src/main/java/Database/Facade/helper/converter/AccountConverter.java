package Database.Facade.helper.converter;

import Domain.Account.AccountDomainObject;
import team_f.jsonconnector.entities.Account;

public class AccountConverter {
    public static AccountDomainObject convert(Account account) {
        AccountDomainObject accountDomain = new AccountDomainObject();

        return accountDomain;
    }
}
