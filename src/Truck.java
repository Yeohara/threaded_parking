import java.util.concurrent.atomic.AtomicInteger;

public class Truck extends Vehicle {
    private static final AtomicInteger counter = new AtomicInteger(0);
    public Truck(long id) {
        this.required_space = 2;
        this.id = id;
    }

    public static int getCounter() {
        return counter.get();
    }

    @Override
    public void causeCarmageddon() {
        System.out.println("Грузовой автомобиль с id = " + this.getId() + " попытался встать в очередь и вызвал Кармагеддон.");
    }

    @Override
    public void enterQueue() {
        System.out.println("Грузовой автомобиль с id = " + this.getId() + " встал в очередь на въезд.");
    }

    @Override
    public void enterParking() {
        counter.incrementAndGet();
        System.out.println("Грузовой автомобиль с id = " + this.getId() + " припарковался.");
    }

    @Override
    public void exitParking() {
        counter.incrementAndGet();
        System.out.println("Грузовой автомобиль с id = " + this.getId() + " покинул парковку.");
    }
}
