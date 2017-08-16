package wa2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Speedy on 15. 5. 2017.
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaccount")
    private Integer idaccount;


    @Column(name = "number")
    private int number;

    @Column(name = "balance")
    private int balance;

    @Column(name = "currency")
    private String currency;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true, name = "person", referencedColumnName = "idperson")
    private Person person;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", fetch = FetchType.EAGER)
    private Collection<Mortgage> mortgages;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "account", fetch = FetchType.EAGER)
    private Collection<Credit> credits;

    public Integer getIdaccount() {
        return idaccount;
    }

    public int getBalance() {

        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Collection<Mortgage> getMortgages() {
        return mortgages;
    }

    public void setMortgages(Collection<Mortgage> mortgages) {
        this.mortgages = mortgages;
    }

    public Collection<Credit> getCredits() {
        return credits;
    }

    public void setCredits(Collection<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idaccount=" + idaccount +
                ", number=" + number +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", person=" + person +
                '}';
    }
}

