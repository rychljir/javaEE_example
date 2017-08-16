package wa2.entities;

import javax.persistence.*;

/**
 * Created by Speedy on 17. 5. 2017.
 */
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "idperson")
public class Client extends Person {

    @Column(name = "number")
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstname=" + getFirstname() + "," +
                "lastname=" + getLastname() + "," +
                "birth=" + getBirth() + "," +
                "number=" + number +
                '}';
    }
}
