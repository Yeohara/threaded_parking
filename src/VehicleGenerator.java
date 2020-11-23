import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VehicleGenerator {
    private final Random random;
    private final AtomicLong currId = new AtomicLong();

    private long getNextId() {
        return currId.getAndIncrement();
    }

    public VehicleGenerator(){
        this.random = new Random();
    }

    protected Vehicle generateVehicle(){
        if (random.nextDouble() > 0.33){
            return new Car(getNextId());
        }
        return new Truck(getNextId());
    }
}
