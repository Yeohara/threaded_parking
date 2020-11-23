public abstract class Vehicle {
    protected int required_space;
    protected long id;

    public long getId() {
        return id;
    }
    public abstract void causeCarmageddon();
    public abstract void enterQueue();
    public abstract void enterParking();
    public abstract void exitParking();
    public int getRequired_space() {
        return required_space;
    }
}

