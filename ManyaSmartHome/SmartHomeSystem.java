package ManyaSmartHome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import ManyaSmartHome.Exceptions.DeviceNotFoundException;
import ManyaSmartHome.Exceptions.InvalidTimeFormatException;

public class SmartHomeSystem {
    private static final Logger logger = Logger.getLogger(SmartHomeSystem.class.getName());

    private final Map<Integer, SmartDevice> devices = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<Trigger> triggers = new ArrayList<>();  // List to store triggers

    // Method to add devices using the factory
    public void addDevice(SmartDevice device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        devices.put(device.getId(), device);
        logger.log(Level.INFO, "[{0} {1} added to the system.]", new Object[]{device.getDeviceType(), device.getId()});
    }

    public void removeDevice(int deviceId) {
        if (devices.containsKey(deviceId)) {
            devices.remove(deviceId);
            logger.log(Level.INFO, "Device with ID {0} has been removed from the system.", deviceId);
        } else {
            logger.log(Level.WARNING, "Attempt to remove non-existing device with ID {0}.", deviceId);
        }
    }

    public void turnOnDevice(int deviceId) {
        SmartDevice device = devices.get(deviceId);
        if (device != null) {
            device.turnOn();
        } else {
            logger.log(Level.WARNING, "Device with ID {0} not found.", deviceId);
        }
    }

    public void turnOffDevice(int deviceId) {
        SmartDevice device = devices.get(deviceId);
        if (device != null) {
            device.turnOff();
        } else {
            logger.log(Level.WARNING, "Device with ID {0} not found.", deviceId);
        }
    }

    public void reportStatus() {
        StringBuilder statusReport = new StringBuilder("Status Report: ");
        
        for (SmartDevice device : devices.values()) {
            statusReport.append(device.getStatus()).append(". ");
        }
        
        // Remove the extra space at the end
        String finalStatusReport = statusReport.toString().trim();
    
        logger.log(Level.INFO, finalStatusReport);
    }
    

    public void setSchedule(int deviceId, String time, String action) 
        throws DeviceNotFoundException, InvalidTimeFormatException  {
    
    SmartDevice device = devices.get(deviceId);
    if (device == null) {
        throw new DeviceNotFoundException("Device with ID " + deviceId + " not found.");
    }

    try {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date scheduledTime = format.parse(time);
        Date currentTime = new Date();
        SimpleDateFormat currentFormat = new SimpleDateFormat("HH:mm");
        String currentTimeString = currentFormat.format(currentTime);
        Date currentFormattedTime = format.parse(currentTimeString);

        if (scheduledTime.after(currentFormattedTime)) {
            long delay = scheduledTime.getTime() - currentFormattedTime.getTime();
            scheduler.schedule(() -> {
                try {
                    if (action.equalsIgnoreCase("on")) {
                        device.turnOn();
                    } else if (action.equalsIgnoreCase("off")) {
                        device.turnOff();
                    } else {
                        // throw new InvalidActionException("Invalid action: " + action);
                    }
                    logger.log(Level.INFO, "[Scheduled action executed: {0} for device {1} at {2}]", 
                            new Object[]{action, device.getId(), time});
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error executing scheduled action: {0}", e.getMessage());
                }
            }, delay, TimeUnit.MILLISECONDS);
            logger.log(Level.INFO, "Scheduled Task - [device: {0}, time: {1}, action: {2}]", 
                    new Object[]{device.getDeviceType(), time, action});
        } else {
            throw new InvalidTimeFormatException("Scheduled time is in the past.");
        }
    } catch (ParseException e) {
        logger.log(Level.SEVERE, "Invalid time format.", e);
        // throw new InvalidTimeFormatException("Invalid time format. Please use HH:mm format.");
    }
}
    public void addTrigger(String type, String operator, double threshold, String action) {
        triggers.add(new Trigger(type, operator, threshold, action));
        
        // Build the trigger condition string
        String condition = type + " " + operator + " " + threshold;
        
        // Format the trigger message
        String triggerMessage = String.format("[{condition: \"%s\", action: \"%s\"}]", condition, action);
        
        // Print the trigger message
        System.out.println("Automated Triggers: " + triggerMessage);
    }
    

    public void checkTriggers() {
        for (SmartDevice device : devices.values()) {
            if (device instanceof Thermostat) {
                double currentTemperature = ((Thermostat) device).getTemperature();

                for (Trigger trigger : triggers) {
                    if (trigger.getType().equals("temperature") && trigger.evaluate(currentTemperature)) {
                        logger.log(Level.INFO, "[Trigger matched. Executing action: {0}]", trigger.getAction());
                        executeAction(trigger.getAction());
                    }
                }
            }
        }
    }

    private void executeAction(String action) {
        String[] parts = action.split("\\(");
        String deviceAction = parts[0].trim();
        int deviceId = Integer.parseInt(parts[1].replace(")", "").trim());

        if ("turnOff".equals(deviceAction)) {
            turnOffDevice(deviceId);
        } else if ("turnOn".equals(deviceAction)) {
            turnOnDevice(deviceId);
        } else {
            logger.log(Level.WARNING, "Unknown action: {0}", deviceAction);
        }
    }
}
