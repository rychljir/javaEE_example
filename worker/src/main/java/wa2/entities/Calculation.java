package wa2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Speedy on 15. 5. 2017.
 */
@Entity
@Table(name = "calculation")
public class Calculation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcalculation")
    private Integer idcalculation;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "result")
    private Integer result;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Integer getIdcalculation() {
        return idcalculation;
    }
}