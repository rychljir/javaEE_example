package wa2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Speedy on 15. 5. 2017.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaddress")
    private Integer idaddress;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "postal")
    private Integer postal;

    @ManyToMany(targetEntity = Person.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "person_address",
            joinColumns = {
                    @JoinColumn(name = "address_idaddress", referencedColumnName = "idaddress")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "person_idperson", referencedColumnName = "idperson")
            }
    )
    private Collection<Person> persons;


    public Integer getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(Integer idaddress) {
        this.idaddress = idaddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostal() {
        return postal;
    }

    public void setPostal(Integer postal) {
        this.postal = postal;
    }

    public Collection<Person> getPersons() {
        return persons;
    }

    public void setPersons(Collection<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idaddress=" + idaddress +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postal=" + postal +
                '}';
    }
}

/*
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String location;
    private String room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer", referencedColumnName = "Id")
    private Customer customer;

    @Basic
    @Column(name = "Id", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Location", nullable = true, length = 0)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "Room", nullable = true, length = 0)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != null ? !id.equals(address.id) : address.id != null) return false;
        if (location != null ? !location.equals(address.location) : address.location != null) return false;
        if (room != null ? !room.equals(address.room) : address.room != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

    @Column(name = "Customer", nullable = true)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", room='" + room + '\'' +
                ", customer=" + customer +
                '}';
    }
}*/
