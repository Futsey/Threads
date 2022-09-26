package concurrent.ref;

public class Main {

    public static void main(String[] args) {
        UserCache userCache = new UserCache();
        User us1 = new User();
        User us2 = new User();
        User us3 = new User();
        User us4 = new User();
        User us5 = new User();
        us1.setId(1);
        us2.setId(2);
        us3.setId(3);
        us4.setId(4);
        us5.setId(5);
        us1.setName("Oleg1");
        us2.setName("Oleg2");
        us3.setName("Oleg3");
        us4.setName("Oleg4");
        us5.setName("Oleg5");
        userCache.add(us1);
        userCache.add(us2);
        userCache.add(us3);
        userCache.add(us4);
        userCache.add(us5);
        System.out.println(userCache.findAll());
    }
}
