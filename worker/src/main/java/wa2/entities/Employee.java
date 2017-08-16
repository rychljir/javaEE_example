package wa2.entities;

import javax.persistence.*;

/**
 * Created by Speedy on 17. 5. 2017.
 */
@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "idperson")
public class Employee extends Person {

    @Column(name = "salary")
    private int salary;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstname=" + getFirstname() + "," +
                "lastname=" + getLastname() + "," +
                "birth=" + getBirth() + "," +
                "salary=" + salary +
                '}';
    }
}