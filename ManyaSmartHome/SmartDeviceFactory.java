package ManyaSmartHome;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SmartDeviceFactory {

    private static final Logger logger = Logger.getLogger(SmartDeviceFactory.class.getName());
    private static SmartDeviceFactory instance;

    private SmartDeviceFactory() {}

    public static SmartDeviceFactory getInstance() {
        if (instance == null) {
            instance = new SmartDeviceFactory();
            // logger.log(Level.INFO, "SmartDeviceFactory instance created.");
        }
        return instance;
    }

    public SmartDevice getInstance(String deviceType, int id) {
        SmartDevice device;
        switch (deviceType.toLowerCase()) {
            case "light":
                device = new LightDevice(id);
                break;
            case "thermostat":
                device = new Thermostat(id, 70); // Default temperature
                break;
            case "doorlock":
                device = new DoorLock(id);
                break;
            default:
                logger.log(Level.SEVERE, "Unknown device type: {0}", deviceType);
                throw new IllegalArgumentException("Unknown device type: " + deviceType);
        }
        // logger.log(Level.INFO, "Created device: {0} with ID {1}", new Object[]{deviceType, id});
        return device;
    }
}
