import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int num_of_parking_spaces = scanner.nextInt();
    int max_queue_size = scanner.nextInt();
    int interval_in = scanner.nextInt();
    int interval_out = scanner.nextInt();
    if (interval_in <= 0 || interval_out <= 0) {
        throw new IllegalArgumentException("Интервалы должны быть больше 0");
    }
    Parking parking = new Parking(num_of_parking_spaces, max_queue_size, interval_in, interval_out);
    }
}
