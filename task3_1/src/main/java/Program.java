import java.util.Scanner;

public class Program {
    static Garage garage = new Garage();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int response = 0;
        do {
            System.out.println("1.Посмотреть");
            System.out.println("2.Добавить");
            System.out.println("3.Удалить");
            System.out.println("0.Выйти");

            response = scan.nextInt();
            if (response <= 4 && response > 0)
                switch (response){
                    case 1: garage.show(); break;
                    case 2: garage.add(); break;
                    case 3: garage.remove(); break;
                }
        } while (response != 0);
    }
}
