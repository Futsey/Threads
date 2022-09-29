package concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean isExist(Account account) {
        return account != null;
    }

    public synchronized boolean isActiveID(Account account) {
        boolean rsl = isExist(account);
        if (account.id() <= 0) {
            throw new IllegalArgumentException("Wrong id number. It must be greater than zero. Now it`s: "
                    .concat(String.valueOf(account.id())));
        }
        return rsl;
    }

    public synchronized boolean isPositiveBalance(Account account) {
        boolean rsl = isExist(account);
        if (account.amount() < 0) {
            throw new IllegalArgumentException("Insufficient funds. Now it`s: "
                    .concat(String.valueOf(account.amount())));
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (isActiveID(from) && isActiveID(to) && isPositiveBalance(from) && (from.amount() - amount) >= 0) {
            update(new Account(fromId, from.amount() - amount));
            update(new Account(toId, to.amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}
