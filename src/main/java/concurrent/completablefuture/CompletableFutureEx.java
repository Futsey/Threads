package concurrent.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Класс является демонстрацией возможностей
 * @see CompletableFuture
 * @author ANDREW PETRUSHIN (JOB4J Project)
 * @version 1.0
 */
public class CompletableFutureEx {

    /**
     * Основной метод для запуска различных версий исполнения кода
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /**
         * Работаем асинхронно (отдельно от основного потока) и ничего не возвращаем
         */
        runAsyncExample();

        /**
         * Работаем асинхронно (отдельно от основного потока) возвращаем объект CompletableFuture<T>
         */
        supplyAsyncExample();

        /**
         * Работаем после выполнения асинхронной задачи и ничего не возвращаем
         */
        thenRunExample();

        /**
         * Работаем после выполнения асинхронной задачи и возвращаем объект CompletableFuture
         */
        thenAcceptExample();

        /**
         * Работаем после выполнения асинхронной задачи и возвращаем объект CompletableFuture в случае вызова метода
         * {@link CompletableFuture#get()}
         */
        thenApplyExample();

        /**
         * Работаем совместив два объекта CompletableFuture и выполняя задачи синхронно
         */
        thenComposeExample();

        /**
         * Работаем совместив два объекта CompletableFuture посредством Bi-Function. Задачи выполняются асинхронно
         */
        thenCombineExample();

        /**
         * Работаем асинхронно, ожидая завершения всех задач с возвратом результата выполнения задач
         */
        allOfExample();

        /**
         * Работаем асинхронно, ожидая завершения всех задач с результатом выполнения первой задачи
         */
        anyOfExample();
    }

    /**
     * Метод-worker. Запускает основную цепочку действий (основной поток)
     * @throws InterruptedException
     */
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * Метод, демонстрирующий работу метода runAsync(), который работает асинхронно (отдельно от основного потока)
     * и ничего не возвращает
     * Триггер {@link #runAsyncExample()}
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    /**
     * Метод - триггер для запуска {@link #goToTrash()}
     */
    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    /**
     * Метод, демонстрирующий работу метода supplyAsync(), который работает асинхронно (отдельно от основного потока)
     * и возвращает объект CompletableFuture<T>
     * Триггер {@link #supplyAsyncExample()}
     * @return product
     */
    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    /**
     * Метод - триггер для запуска {@link #buyProduct(String product)}
     */
    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * Callback - метод (метод, который будет вызван после выполнения асинхронной задачи),
     * в данном случае после выполнения {@link #goToTrash()}
     * и ничего не возвращает
     */
    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын: я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын: Я помыл руки");
        });
        iWork();
    }

    /**
     * Callback - метод (метод, который будет вызван после выполнения асинхронной задачи),
     * в данном случае после выполнения {@link #buyProduct(String product)}
     * но возвращает объект CompletableFuture
     * @return bm.get()
     */
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * Callback - метод (метод, который будет вызван после выполнения асинхронной задачи),
     * в данном случае после выполнения {@link #buyProduct(String product)}
     * но возвращает объект CompletableFuture только в случае вызова get()
     * @return bm.get()
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    /**
     * Метод для совмещения двух объектов CompletableFuture.
     * Данный метод используется, если действия зависимы. Т.е. сначала должно выполниться одно, а только потом другое
     * В данном случае после выполнения {@link #goToTrash()} выполняется {@link #buyProduct(String product)}
     */
    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get();
    }

    /**
     * Метод для совмещения двух объектов CompletableFuture.
     * Данный метод используется, если действия могут быть выполнены независимо друг от друга.
     * В качестве второго аргумента, нужно передавать BiFunction – функцию,
     * которая преобразует результаты двух задач во что-то одно.
     * В данном случае выполнение {@link #buyProduct(String product)}
     * выполняется асинхронно с {@link #buyProduct(String product)}
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    /**
     * Метод, реализующий асинхронную версию {@link #allOfExample()}
     * без возврата результата выполнения метода.
     */
    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    /**
     * Метод, ожидающий завершения всех задач {@link CompletableFuture#allOf(CompletableFuture[])}.
     */
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * Метод, реализующий асинхронную версию {@link #allOfExample()}
     * с возвратом результата выполнения метода.
     * @return name
     */
    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }

    /**
     * Метод, ожидающий завершения всех задач {@link CompletableFuture#anyOf(CompletableFuture[])}.
     * Метод возвращает CompletableFuture<Object>. Результатом будет первая выполненная задача.
     * На том же примере мы можем, например, узнать, кто сейчас моет руки.
     * Результаты запуск от запуска будут различаться.
     */
    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }
}
