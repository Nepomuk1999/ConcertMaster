package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.AccountEntity;
import team_f.database_wrapper.enums.AccountRole;
import team_f.database_wrapper.interfaces.Editeable;
import team_f.domain.entities.Account;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AccountFacade extends BaseDatabaseFacade<AccountEntity, Account> {
    public AccountFacade() {
        super();
    }

    public AccountFacade(EntityManager session) {
        super(session);
    }

    protected AccountEntity convertToAccountEntity(Account account) {
        AccountEntity entity = new AccountEntity();
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

    public List<Account> getAllUserNames() {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from AccountEntity");

        List<AccountEntity> accountEntities = query.getResultList();
        List<Account> accounts = new ArrayList<>();

        for (AccountEntity accountEntity : accountEntities) {
            Account account;
            account = convertToAccount(accountEntity);

            accounts.add(account);
        }

        return accounts;
    }


    @Override
    public int add(Account value) {
        return 0;
    }

    @Override
    public int update(Account value) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
