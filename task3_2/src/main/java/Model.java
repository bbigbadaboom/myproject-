import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String model;

    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private Producer producer;

    public Model(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }


    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Producer getProducer() {
        return producer;
    }
}
