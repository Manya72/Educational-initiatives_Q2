package ManyaSmartHome;
public class Thermostat implements SmartDevice {
    private int id;
    private int temperature;

    public Thermostat(int id, int temperature) {
        this.id = id;
        this.temperature = temperature;
    }

    @Override
    public void turnOn() {
        System.out.println("Thermostat " + id + " is turned on.");
    }

    @Override
    public void turnOff() {
        System.out.println("Thermostat " + id + " is turned off.");
    }

    @Override
    public String getStatus() {
        return "Thermostat " + id + " is set to " + temperature + " degrees.";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDeviceType() {
        return "Thermostat";
    }

    @Override
    public void update(String status) {
        // Implementation for observer update (if necessary)
        System.out.println("Thermostat " + id + " received update: " + status);
    }

    // Getter for temperature
    public int getTemperature() {
        return temperature;
    }

    // Setter for temperature
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("Thermostat " + id + " temperature set to " + temperature + " degrees.");
        // Notify observers if necessary
        // notifyObservers("Temperature changed to " + temperature);
    }
}
