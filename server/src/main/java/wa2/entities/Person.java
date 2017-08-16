package wa2.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Speedy on 15. 5. 2017.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperson")
    private Integer idperson;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth")
    private String birth;

    @ManyToMany(targetEntity = Address.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_address",
            joinColumns = {
                    @JoinColumn(name = "person_idperson", referencedColumnName = "idperson")

            },
            inverseJoinColumns = {
                    @JoinColumn(name = "address_idaddress", referencedColumnName = "idaddress")
            }
    )
    private Collection<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<Account> accounts;

    public Integer getIdperson() {
        return idperson;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}