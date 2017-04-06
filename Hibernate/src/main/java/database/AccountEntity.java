package database;

import javax.persistence.*;

/**
 * Created by Home on 06.04.2017.
 */

//, catalog = ""aus @Table am Schluss entfernt
@Entity
@Table(name = "Account", schema = "sem4_team2")
public class AccountEntity {
    private int accountId;
    private String username;
    private String password;
    private Enum accountRole;

    @Id
    @Column(name = "accountID", nullable = false)
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "accountRole", nullable = false)
    public Enum getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(Enum accountRole) {
        this.accountRole = accountRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (accountId != that.accountId) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (accountRole != null ? !accountRole.equals(that.accountRole) : that.accountRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accountRole != null ? accountRole.hashCode() : 0);
        return result;
    }
}
