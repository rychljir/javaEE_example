package wa2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Speedy on 15. 5. 2017.
 */
@Entity
@Table(name = "credit")
public class Credit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcredit")
    private Integer idcredit;

    @Column(name = "value")
    private int value;

    @Column(name = "percentage")
    private int percentage;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account", referencedColumnName = "idaccount")
    private Account account;

    public Integer getIdcredit() {
        return idcredit;
    }

    public int getValue() {

        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "idcredit=" + idcredit +
                ", value=" + value +
                ", percentage=" + percentage +
                ", account=" + account +
                '}';
    }
}

