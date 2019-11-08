import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String name, String login, String password) {
        super(name, login, password);
    }

    public List<String> inputHorses(){
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < 7; i++)
        {
            System.out.println("Введите имя лошади " + (i + 1)+ ": ");
            list.add(in.nextLine());
        }
        return list;
    }
}
