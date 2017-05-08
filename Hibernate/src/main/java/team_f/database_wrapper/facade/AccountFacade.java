package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.AccountEntity;
import team_f.database_wrapper.enums.AccountRole;
import team_f.domain.entities.Account;
import javax.persistence.EntityManager;

public class AccountFacade extends BaseDatabaseFacade {
    public AccountFacade() {
        super();
    }

    public AccountFacade(EntityManager session) {
        super(session);
    }

    protected AccountEntity convertToAccountEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setAccountId(account.getAccountID());
        entity.setUsername(account.getUsername());
        entity.setPassword(account.getPassword());
        entity.setAccountRole(AccountRole.valueOf(String.valueOf(account.getRole())));

        return entity;
    }

    public Account convertToAccount(AccountEntity accountEntity) {
        Account account = new Account();
        account.setAccountID(accountEntity.getAccountId());
        account.setUsername(accountEntity.getUsername());
        account.setRole(team_f.domain.enums.AccountRole.valueOf(accountEntity.getAccountRole().toString()));

        return account;
    }
}
