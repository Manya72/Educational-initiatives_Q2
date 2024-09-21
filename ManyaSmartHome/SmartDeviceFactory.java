package ManyaSmartHome;
public class SmartDeviceFactory {

    // Singleton instance
    private static SmartDeviceFactory instance;

    // Private constructor to prevent instantiation from other classes
    private SmartDeviceFactory() {}

    // Method to get the singleton instance of the DeviceFactory
    public static SmartDeviceFactory getInstance() {
        if (instance == null) {
            instance = new SmartDeviceFactory();
        }
        return instance;
    }

    // Factory method to create devices
    public SmartDevice getInstance(String deviceType, int id) {
        switch (deviceType.toLowerCase()) {
            case "light":
                return new LightDevice(id);
            case "thermostat":
                return new Thermostat(id, 70); // Default temperature
            case "doorlock":
                return new DoorLock(id);
            default:
                throw new IllegalArgumentException("Unknown device type: " + deviceType);
        }
    }
}
