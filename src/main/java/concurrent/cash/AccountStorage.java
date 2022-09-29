package concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (account != null) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (account.id() > 0) {
            accounts.replace(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(int id) {
        boolean rsl = false;
        if (accounts.containsKey(id)) {
            accounts.remove(id);
            rsl = true;
        }
        return rsl;
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> rsl = Optional.empty();
        if (accounts.containsKey(id)) {
            rsl = Optional.of(accounts.get(id));
        }
        return rsl;
    }

    public synchronized boolean isExist(Account account) {
        boolean rsl = false;
        if (account != null) {
            rsl = true;
        }
        if (account.id() <= 0) {
            throw new NoSuchElementException("Wrong id number. It must be greater than zero. Now it`s: "
                    .concat(String.valueOf(account.id())));
        }
        if (account.amount() < 0) {
            throw new NoSuchElementException("Insufficient funds. Now it`s: "
                    .concat(String.valueOf(account.amount())));
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (isExist(from) && isExist(to) && (from.amount() - amount) >= 0) {
            update(new Account(fromId, from.amount() - amount));
            update(new Account(toId, to.amount() + amount));
            rsl = true;
        }
        return rsl;
    }
}
