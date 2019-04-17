import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Car {
    public String model;

    public String producer;

    Car(){}

    public String info()
    {
        return String.format("%s %s", producer, model);
    }
}
