import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int num_of_parking_spaces = scanner.nextInt();
    if (num_of_parking_spaces < 1) {
        throw new IllegalArgumentException("Размер парковки должен быть больше 0. Нет количества мест на парковке - нет парковки.");
    }

    int max_queue_size = scanner.nextInt();
    if (max_queue_size < 1) {
        throw new IllegalArgumentException("Размер очереди должен быть больше 0. Нет длины очереди - нет дороги на парковку.");
    }

    int interval_in = scanner.nextInt();
    int interval_out = scanner.nextInt();
    if (interval_in <= 0 || interval_out <= 0) {
        throw new IllegalArgumentException("Интервалы должны быть больше 0.");
    }

    new Parking(num_of_parking_spaces, max_queue_size, interval_in, interval_out);
    }
}
