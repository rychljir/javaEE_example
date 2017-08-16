package wa2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Speedy on 15. 5. 2017.
 */
@Entity
@Table(name = "mortgage")
public class Mortgage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmortgage")
    private Integer idmortgage;

    @Column(name = "value")
    private int value;

    @Column(name = "years")
    private int years;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account", referencedColumnName = "idaccount")
    private Account account;

    public Integer getIdmortgage() {
        return idmortgage;
    }

    public int getValue() {

        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Mortgage{" +
                "idmortgage=" + idmortgage +
                ", value=" + value +
                ", years=" + years +
                ", account=" + account +
                '}';
    }
}

