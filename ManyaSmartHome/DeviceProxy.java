package ManyaSmartHome;

import java.util.logging.Level;
import java.util.logging.Logger;

// Proxy class to control access to SmartDevice based on user role
public class DeviceProxy {
    private static final Logger logger = Logger.getLogger(DeviceProxy.class.getName());

    private SmartDevice device;
    private String userRole;

    // Constructor to initialize the device and user role
    public DeviceProxy(SmartDevice device, String userRole) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null.");
        }
        if (userRole == null || userRole.isEmpty()) {
            throw new IllegalArgumentException("User role cannot be null or empty.");
        }
        this.device = device;
        this.userRole = userRole;
    }

    // Method to turn on the device if the user has access
    public void turnOn() {
        if (!hasAccess()) {
            logger.log(Level.WARNING, "Access Denied: {0} does not have permission to turn on the device.", userRole);
            return;
        }
        device.turnOn();
        logger.log(Level.INFO, "Device {0} turned on by {1}.", new Object[]{device.getId(), userRole});
    }

    // Method to turn off the device if the user has access
    public void turnOff() {
        if (!hasAccess()) {
            logger.log(Level.WARNING, "Access Denied: {0} does not have permission to turn off the device.", userRole);
            return;
        }
        device.turnOff();
        logger.log(Level.INFO, "Device {0} turned off by {1}.", new Object[]{device.getId(), userRole});
    }

    // Helper method to check if the user has admin access
    private boolean hasAccess() {
        return "admin".equals(userRole);
    }

    // Method to get the device's current status
    public String getStatus() {
        return device.getStatus();
    }
}
