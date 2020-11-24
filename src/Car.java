import java.util.concurrent.atomic.AtomicInteger;

public class Car extends Vehicle {
    /** Поле общего счётчика всех {@link Car}-объектов на парковке */
    private static final AtomicInteger counter = new AtomicInteger(0);
    public Car(long id) {
        this.required_space = 1;
        this.id = id;
    }

    public static int getCounter() {
        return counter.get();
    }

    @Override
    public void causeCarmageddon() {
        System.out.println("Легковой автомобиль с id = " + this.getId() + " попытался встать в очередь и вызвал Кармагеддон.");
    }

    @Override
    public void enterQueue() {

        System.out.println("Легковой автомобиль с id = " + this.getId() + " встал в очередь на въезд.");
    }

    @Override
    public void enterParking() {
        counter.incrementAndGet();
        System.out.println("Легковой автомобиль с id = " + this.getId() + " припарковался.");
    }

    @Override
    public void exitParking() {
        counter.decrementAndGet();
        System.out.println("Легковой автомобиль с id = " + this.getId() + " покинул парковку.");
    }
}
