import java.util.*;

public class Totalizator {
    private static List<Client> users;
    private static List<String> horses;
    private static User currentUser;

    public static void main(String[] args)
    {
        Admin admin = new Admin("Admin", "Admin", "password");
        users = new ArrayList<Client>();
        horses = admin.inputHorses();
        Scanner in = new Scanner(System.in);
        int code;
        do {
            if(currentUser == null)
            {
                System.out.println("Меню:\n1) Добавить пользователя\n2) Найти пользователя\n3) Запустить забег\n0) Выход");
                code = in.nextInt();
                switch (code)
                {
                    case 0:
                        break;
                    case 1:
                        in.nextLine();
                        System.out.println("Введите имя: ");
                        String name = in.nextLine();
                        System.out.println("Введите логин: ");
                        String login = in.nextLine();
                        System.out.println("Введите пароль: ");
                        String password = in.nextLine();
                        addUser(name, login, password);
                        break;
                    case 2:
                        in.nextLine();
                        System.out.println("Введите логин: ");
                        String _login = in.nextLine();
                        System.out.println("Введите пароль: ");
                        String _password = in.nextLine();
                        currentUser = findUser(_login, _password);
                        break;
                    case 3:
                        boolean flag = false;
                        for(Client c : users)
                        {
                            if(c.getParlays().size() > 0)
                            {
                                flag = true;
                                break;
                            }
                        }
                        if(flag)
                            generateResults();
                        else
                            System.out.println("Нет ставок");
                        break;
                        default:
                            System.out.println("Неверный пункт меню");
                }
            }
            else
            {
                System.out.println("Меню:\n1) Сделать ставку\n2) Посмотреть ставку\n9) Назад");
                code = in.nextInt();
                switch (code)
                {
                    case 9:
                        currentUser = null;
                        break;
                    case 1:
                        if(((Client)currentUser).getParlays().size() > 0)
                        {
                            System.out.println("Вы уже сделали ставку на этот забег.");
                            break;
                        }
                        in.nextLine();
                        System.out.println("Введите имя лошади: ");
                        String horse = in.nextLine();
                        System.out.println("Введите сумму: ");
                        int sum = in.nextInt();
                        if(horses.indexOf(horse) >= 0)
                            ((Client)currentUser).addParlay(horse, sum);
                        else
                            System.out.println("Такой лошади не существует");
                        break;
                    case 2:
                        for(Parlay p : ((Client)currentUser).getParlays())
                        {
                            System.out.println("Ставка на лошадь " + p.getHorse() + ", сумма: " + p.getSum());
                        }
                        break;
                    default:
                        System.out.println("Неверный пункт меню");
                }
            }
        }while(code != 0);
    }

    private static void addUser(String name, String login, String password)
    {
        try {
            users.add(new Client(name, login, password));
            System.out.println("Пользователь " + name + " успешно добавлен.");
        }catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }

    private static User findUser(String login, String password)
    {
        for(Client u : users)
        {
            if(u.enter(login, password))
            {
                System.out.println("Пользователь " + u.getName() + " успешно найден.");
                return u;
            }
        }
        System.out.println("Пользователь не найден.");
        return null;
    }

    private static void generateResults()
    {
        SortedMap<Integer, String> res = new TreeMap<>();
        int num;
        for(int i = 0; i < 7; i++)
        {
            num = (int)(Math.random() * 30 + 1);
            res.put(num, horses.get(i));
        }
        System.out.println("Таблица забега:");
        Iterator iterator = res.entrySet().iterator();
        SortedMap.Entry entry;
        while(iterator.hasNext())
        {
            entry = (SortedMap.Entry) iterator.next();
            System.out.println("Лошадь " + entry.getValue() + " пробежала за " + entry.getKey());
        }
        calculateMoney(res);
        clearAllParlays();
    }

    private static void clearAllParlays()
    {
        for(Client u : users)
        {
            u.clearParlays();
        }
    }

    private static void calculateMoney(SortedMap list)
    {
        int sum = 0;
        Iterator iterator = list.entrySet().iterator();
        SortedMap.Entry entry = (SortedMap.Entry) iterator.next();
        String winner = (String)entry.getValue();
        for(Client c : users) //подсчет общей суммы
        {
            sum += c.getParlays().get(0).getSum();
        }
        int winCount = 0;
        for(Client c : users) // возвращаем победителям их вложения
        {
            if(c.getParlays().get(0).getHorse().equals(winner)) {
                sum -= c.getParlays().get(0).getSum();
                winCount++;
            }
        }
        int avrNum = 0;
        if(winCount > 0)
        {
            avrNum = sum / winCount;
        }
        for(Client c : users) // выдаем награду победителям
        {
            if(c.getParlays().get(0).getHorse().equals(winner))
            {
                System.out.println("Пользователь " + c.getName() + " выигрывает и получает +" + avrNum);
            }
        }
    }
}
