package concurrent.cache;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Integer, Base> map = new HashMap<>();
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        map.put(base.getId(), base);
        cache.add(base);

        Base user1 = map.get(1);
        user1.setName("Base user1");
        System.out.println("CACHE: ".concat(String.valueOf(cache.getCache(user1))));

        Base user2 = map.get(1);
        user1.setName("Base user2");
        System.out.println("CACHE: ".concat(String.valueOf(cache.getCache(user2))));

        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);

        System.out.println(map);
    }
}
