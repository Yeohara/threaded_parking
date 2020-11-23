import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Parking {
    private int num_of_parking_spaces;
    private final int max_queue_size;
    private int num_of_Trucks = 0;
    private int num_of_Cars = 0;

    private final int interval_in;
    private final int interval_out;
    private final Object lock = new Object();
    private final Queue<Vehicle> queue;
    private final ArrayList<Vehicle> parking;
    private final VehicleGenerator vehicleGenerator;

    public Parking(int num_of_parking_spaces, int max_queue_size, int interval_in, int interval_out){
        this.num_of_parking_spaces = num_of_parking_spaces;
        this.max_queue_size = max_queue_size;
        this.parking = new ArrayList<>();
        this.queue = new LinkedList<>();
        this.interval_in = interval_in;
        this.interval_out = interval_out;
        this.vehicleGenerator = new VehicleGenerator();
        new Thread(new queueIn()).start();
        new Thread(new parkingIn()).start();
        new Thread(new parkingOut()).start();
        new Thread(new printState()).start();
    }



    private class printState implements Runnable {
        public void run() {
            try {
                while (true) {
                    Thread.sleep(5 * 1000);
                    synchronized (lock) {
                        System.out.println("- Свободных мест: " + num_of_parking_spaces);
                        System.out.println("- Занято мест: " + (num_of_Trucks * 2 + num_of_Cars) + " (из них " + num_of_Cars + " легковых и " + num_of_Trucks + " грузовых авто)");
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
                while (true) {
                    synchronized (lock) {

                        Vehicle vehicle = vehicleGenerator.generateVehicle();
                        int vehicle_size = vehicle.getRequired_space();
                        if (queue.size() + 1 > max_queue_size) {
                            if (vehicle_size == 1) {
                                num_of_Cars++;
                                System.out.println("Легковой автомобиль с id = " + vehicle.getId() + " попытался встать в очередь и вызвал Кармагеддон.");
                            }
                            else {
                                num_of_Trucks++;
                                System.out.println("Грузовой автомобиль с id = " + vehicle.getId() + " попытался встать в очередь и вызвал Кармагеддон.");
                            }
                            System.exit(1);
                        }
                        if (num_of_parking_spaces - vehicle_size >= 0 && queue.isEmpty()) {
                            parking.add(vehicle);
                            num_of_parking_spaces -= vehicle_size;
                            if (vehicle_size == 1) {
                                num_of_Cars++;
                                System.out.println("Легковой автомобиль с id = " + vehicle.getId() + " припарковался.");
                            }
                            else {
                                num_of_Trucks++;
                                System.out.println("Грузовой автомобиль с id = " + vehicle.getId() + " припарковался.");
                            }
                        } else {
                            queue.add(vehicle);
                            if (vehicle_size == 1) {
                                System.out.println("Легковой автомобиль с id = " + vehicle.getId() + " встал в очередь на въезд.");
                            }
                            else {
                                System.out.println("Грузовой автомобиль с id = " + vehicle.getId() + " встал в очередь на въезд.");
                            }
                        }
                    }
                    Thread.sleep((vehicleGenerator.random.nextInt(interval_in) + 1) * 1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Поток очереди прервался.");
            }
        }
    }

    private class parkingIn implements Runnable {
        public void run() {
            try {
                while (true) synchronized (lock) {
                    if (!queue.isEmpty() && num_of_parking_spaces != 0) {
                        Vehicle vehicle = queue.peek();
                        int vehicle_size = vehicle.getRequired_space();
                        if (num_of_parking_spaces - vehicle_size >= 0) {
                            queue.remove();
                            parking.add(vehicle);
                            num_of_parking_spaces -= vehicle_size;
                            if (vehicle_size == 1) {
                                num_of_Cars++;
                                System.out.println("Легковой автомобиль с id = " + vehicle.getId() + " припарковался.");
                            } else {
                                num_of_Trucks++;
                                System.out.println("Грузовой автомобиль с id = " + vehicle.getId() + " припарковался.");
                            }
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
                while (true) {
                    synchronized (lock) {
                        try {
                            int removing_number = vehicleGenerator.random.nextInt(num_of_Cars + num_of_Trucks);

                            Vehicle vehicle = parking.get(removing_number);
                            int vehicle_size = vehicle.getRequired_space();
                            parking.remove(removing_number);
                            num_of_parking_spaces += vehicle_size;
                            if (vehicle_size == 1) {
                                num_of_Cars--;
                                System.out.println("Легковой автомобиль с id = " + vehicle.getId() + " покинул парковку.");
                            } else {
                                num_of_Trucks--;
                                System.out.println("Грузовой автомобиль с id = " + vehicle.getId() + " покинул парковку.");
                            }
                        } catch (IndexOutOfBoundsException | IllegalArgumentException ignored) {}
                    }
                    Thread.sleep((vehicleGenerator.random.nextInt(interval_out) + 1) * 1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Поток парковки прервался.");
            }
        }
    }
}
