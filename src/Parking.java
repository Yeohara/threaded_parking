import java.util.*;

public class Parking {
    private int num_of_parking_spaces;
    private final int max_queue_size;
    private boolean running;
    private final int interval_in;
    private final int interval_out;
    private final Object lock = new Object();
    private final Queue<Vehicle> queue;
    private final ArrayList<Vehicle> parking;
    private final VehicleGenerator vehicleGenerator;
    private final Random random;

    public Parking(int num_of_parking_spaces, int max_queue_size, int interval_in, int interval_out){
        this.num_of_parking_spaces = num_of_parking_spaces;
        this.max_queue_size = max_queue_size;
        this.parking = new ArrayList<>();
        this.queue = new LinkedList<>();
        this.interval_in = interval_in;
        this.interval_out = interval_out;
        this.vehicleGenerator = new VehicleGenerator();
        this.random = new Random();
        this.running = true;
        new Thread(new exitState()).start();
        new Thread(new queueIn()).start();
        new Thread(new parkingIn()).start();
        new Thread(new parkingOut()).start();
        new Thread(new printState()).start();
    }


    private class exitState implements Runnable {
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (running)
                if (scanner.next().equals("q")) {
                    running = false;
                }
        }
    }

    private class printState implements Runnable {
        public void run() {
            try {
                while (running) {
                    Thread.sleep(5 * 1000);
                    synchronized (lock) {
                        System.out.println("- Свободных мест: " + num_of_parking_spaces);
                        System.out.println("- Занято мест: " + (Truck.getCounter() * 2 + Car.getCounter()) + " (из них " + Car.getCounter() + " легковых и " + Truck.getCounter()  + " грузовых авто)");
                        System.out.println("- Автомобилей, ожидающих в очереди: " + queue.size());
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Поток статистики прервался.");
            }
        }
    }

    private class queueIn implements Runnable {
        public void run() {
            try {
                while (running) {
                    synchronized (lock) {

                        Vehicle vehicle = vehicleGenerator.generateVehicle();
                        int vehicle_size = vehicle.getRequired_space();
                        if (queue.size() + 1 > max_queue_size) {
                            vehicle.causeCarmageddon();
                            System.exit(1);
                        }
                        if (num_of_parking_spaces - vehicle_size >= 0 && queue.isEmpty()) {
                            parkVehicle(vehicle);
                        } else {
                            queue.add(vehicle);
                            vehicle.enterQueue();
                        }
                    }
                    Thread.sleep((random.nextInt(interval_in) + 1) * 1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Поток очереди прервался.");
            }
        }


    }

    private void parkVehicle(Vehicle vehicle) {
        parking.add(vehicle);
        num_of_parking_spaces -= vehicle.getRequired_space();
        vehicle.enterParking();
    }

    private class parkingIn implements Runnable {
        public void run() {
            try {
                while (running) synchronized (lock) {
                    if (!queue.isEmpty() && num_of_parking_spaces != 0) {
                        Vehicle vehicle = queue.peek();
                        int vehicle_size = vehicle.getRequired_space();
                        if (num_of_parking_spaces - vehicle_size >= 0) {
                            queue.remove();
                            parkVehicle(vehicle);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class parkingOut implements Runnable {
        public void run() {
            try {
                while (running) {
                    synchronized (lock) {
                        try {
                            int removing_number = random.nextInt(Car.getCounter() + Truck.getCounter());

                            Vehicle vehicle = parking.get(removing_number);
                            int vehicle_size = vehicle.getRequired_space();
                            parking.remove(removing_number);
                            num_of_parking_spaces += vehicle_size;
                            vehicle.exitParking();
                        } catch (IndexOutOfBoundsException | IllegalArgumentException ignored) {}
                    }
                    Thread.sleep((random.nextInt(interval_out) + 1) * 1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Поток парковки прервался.");
            }
        }
    }
}
