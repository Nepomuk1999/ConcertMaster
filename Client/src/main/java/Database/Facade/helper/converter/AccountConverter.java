package Database.Facade.helper.converter;

import Domain.Account.AccountDomainObject;
import team_f.jsonconnector.entities.Account;

public class AccountConverter {
    public static AccountDomainObject convert(Account account) {
        AccountDomainObject accountDomain = new AccountDomainObject();

        // nothing to do, because it is not implemented in the code from team E

        return accountDomain;
    }

    public static Account convert(AccountDomainObject account) {
        Account result = new Account();

        // nothing to do, because it is not implemented in the code from team E

        return result;
    }
}
