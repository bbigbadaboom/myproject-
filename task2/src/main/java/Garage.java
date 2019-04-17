import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Garage {
    List<Car> cars = new LinkedList<Car>();
    Scanner scan = new Scanner(System.in);

    final List<Car> carTypes;

    Garage(){
        carTypes = load("cars.json");
    }

    private List<Car> load(String filename){
        try{
            return Arrays.asList(new ObjectMapper().readValue(new File(filename), Car[].class));
        }
        catch (FileNotFoundException e){
            System.err.println("Check cars.json file");
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            System.err.println("Check cars.json file");
            System.err.println(e.getMessage());
        }
        return new LinkedList<>();
    }

    public void add(){
        System.out.println("Выберите машину для добавления");
        int i = 0;
        for (Car car: carTypes)
            System.out.println(String.format("%d. %s", i++, car.info()));
        System.out.println("Любое другое число/знак чтобы вернутся назад");

        i = scan.nextInt();

        if (i >= 0 && i < carTypes.size())
            cars.add(carTypes.get(i));
        else
            System.out.println("Машина не выбрана");
    }

    public void remove()
    {
        System.out.println("Выберите машину для удаления");
        int i = 0;
        for (Car car: cars)
            System.out.println(String.format("%d. %s", i++, car.info()));
        System.out.println("Любое другое число/знак чтобы вернутся назад");

        i = scan.nextInt();

        if (i >= 0 && i < carTypes.size())
            cars.remove(i);
        else
            System.out.println("Машина не выбрана");
    }

    public void show(){
        System.out.println("ГАРАЖ. Количество машин: " + String.valueOf(cars.size()));
        int i = 0;
        for (Car car: cars)
            System.out.println(String.format("%d. %s", i++, car.info()));
        if (cars.size() == 0)
            System.out.println("пусто");
        System.out.println("Нажмите enter, чтобы продолжить");
        scan.nextLine();
        scan.nextLine();
    }
}
