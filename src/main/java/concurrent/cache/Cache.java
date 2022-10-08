package concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            value.setName(model.getName());
            value.setVersion(value.getVersion() + 1);
            return value;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base getCache(Base model) {
        return memory.get(model.getId());
    }
}
