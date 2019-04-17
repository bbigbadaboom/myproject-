import javax.persistence.*;

@Entity
@Table(name = "places")
public class GaragePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.PERSIST)
    private Model model;
    //private Long model_id;


    public GaragePlace(Model model){
        this.model = model;
    }

    public GaragePlace(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public void setModeld(Long id) {
//        this.model_id = id;
//    }
//
//    public Long getModelId() {
//        return model_id;
//    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
