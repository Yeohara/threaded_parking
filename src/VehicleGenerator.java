import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс генерирующий машины
 */
public class VehicleGenerator {
    private final double random_chance = 0.33;
    private final Random random;
    private final AtomicLong currId = new AtomicLong();

    private long getNextId() {
        return currId.getAndIncrement();
    }

    /**
     * Конструктор - создание нового объекта-генератора
     */
    public VehicleGenerator(){
        this.random = new Random();
    }

    /**
     * Функция генерации случайной машины. Шанс создания легкового автомобиля 1 - {@link VehicleGenerator#random_chance}; шанс создания грузового автомобиля {@link VehicleGenerator#random_chance}.
     * @return возвращает {@link Vehicle}
     */
    protected Vehicle generateVehicle(){
        if (random.nextDouble() > random_chance){
            return new Car(getNextId());
        }
        return new Truck(getNextId());
    }
}
