/**
 * Абстрактный класс машины
 */
public abstract class Vehicle {
    /** Количество занимаего места машиной на парковке */
    protected int required_space;
    /** Уникальный ID машины */
    protected long id;

    public long getId() {
        return id;
    }

    /**
     * Функция вызова сообщения о кармагеддоне
     */
    public abstract void causeCarmageddon();
    /**
     * Функция вызова сообщения о попадании машины во входную очередь
     */
    public abstract void enterQueue();
    /**
     * Функция вызова сообщения о заезде машины на парковку
     */
    public abstract void enterParking();
    /**
     * Функция вызова сообщения о выезде машины с парковки
     */
    public abstract void exitParking();
    public int getRequired_space() {
        return required_space;
    }
}

