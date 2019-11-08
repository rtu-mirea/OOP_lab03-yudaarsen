import java.util.ArrayList;
import java.util.List;

public class Client extends User{
    private List<Parlay> parlays;

    public Client(String name, String login, String password){
        super(name, login, password);
        parlays = new ArrayList<Parlay>();
    }

    public void addParlay(String horse, int sum){
        try {
            parlays.add(new Parlay(horse, sum));
        }catch (IllegalArgumentException e){
            System.out.println(e.toString());
        }
    }

    public void clearParlays(){
        parlays.clear();
    }

    public List<Parlay> getParlays(){
        return parlays;
    }
}
