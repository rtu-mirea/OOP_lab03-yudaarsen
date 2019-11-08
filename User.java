public class User {
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) throws IllegalArgumentException
    {
        if(name.length() == 0 || password.length() == 0 || login.length() == 0) throw new IllegalArgumentException("Неверные значения пользователя");
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public boolean enter(String login, String password)
    {
        return this.login.equals(login) && this.password.equals(password);
    }

    public String getName()
    {
        return name;
    }
}
