package ManyaSmartHome;
public class DoorLock implements SmartDevice {
    private int id;
    private boolean isLocked;

    public DoorLock(int id) {
        this.id = id;
        this.isLocked = true; // Default state
    }

    @Override
    public void turnOn() {
        System.out.println("DoorLock " + id + " is activated.");
    }

    @Override
    public void turnOff() {
        System.out.println("DoorLock " + id + " is deactivated.");
    }

    @Override
    public String getStatus() {
        return "DoorLock " + id + " is " + (isLocked ? "Locked" : "Unlocked");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDeviceType() {
        return "DoorLock";
    }

    @Override
    public void update(String status) {
        // Implementation for observer update (if necessary)
        System.out.println("DoorLock " + id + " received update: " + status);
    }
}
