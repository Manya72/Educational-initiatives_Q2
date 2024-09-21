package ManyaSmartHome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SmartHomeSystem {
    final Map<Integer, SmartDevice> devices = new HashMap<>();
    private final SmartDeviceFactory deviceFactory;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      private final List<Trigger> triggers = new ArrayList<>();  // List to sto

    public SmartHomeSystem() {
        // Get the singleton instance of DeviceFactory
        this.deviceFactory = SmartDeviceFactory.getInstance();
    }

    // Method to add devices using the factory
    public void addDevice(SmartDevice device) {
        devices.put(device.getId(), device);
        System.out.println(device.getDeviceType() + " " + device.getId() + " added to the system.");
    }
    public void removeDevice(int deviceId) {
        if (devices.containsKey(deviceId)) {
            devices.remove(deviceId);
            System.out.println("Device with ID " + deviceId + " has been removed from the system.");
        } else {
            System.out.println("Device with ID " + deviceId + " not found.");
        }
    }
    
    // Methods to control the devices
    public void turnOnDevice(int deviceId) {
        SmartDevice device = devices.get(deviceId);
        if (device != null) {
            device.turnOn();
        } else {
            System.out.println("Device with ID " + deviceId + " not found.");
        }
    }

    public void turnOffDevice(int deviceId) {
        SmartDevice device = devices.get(deviceId);
        if (device != null) {
            device.turnOff();
        } else {
            System.out.println("Device with ID " + deviceId + " not found.");
        }
    }

    public void reportStatus() {
        for (SmartDevice device : devices.values()) {
            System.out.println(device.getStatus());
        }
    }

    // Method to notify all devices with a status update
    public void notifyAllDevices(String status) {
        for (SmartDevice device : devices.values()) {
            device.update(status);  // Send the status update to each device
        }
    }

    // Method to schedule turning on/off a device at a specific time
    public void setSchedule(int deviceId, String time, String action) throws Exception {
        SmartDevice device = devices.get(deviceId);
        if (device != null) {
            try {
                // Parse the scheduled time
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date scheduledTime = format.parse(time);
    
                // Get current time in HH:mm format
                Date currentTime = new Date();
                SimpleDateFormat currentFormat = new SimpleDateFormat("HH:mm");
                String currentTimeString = currentFormat.format(currentTime);
                Date currentFormattedTime = format.parse(currentTimeString);
                System.out.println(currentFormattedTime);
                System.out.println(scheduledTime);
                // Compare scheduled time with the current time
                if (scheduledTime.after(currentFormattedTime)) {
                    long delay = scheduledTime.getTime() - currentFormattedTime.getTime();
    
                    // Schedule the task based on the time difference
                    System.out.println(currentFormattedTime);
                    System.out.println(scheduledTime);
                    scheduler.schedule(() -> {
                        try {
                            if (action.equalsIgnoreCase("on")) {
                                device.turnOn();
                            } else if (action.equalsIgnoreCase("off")) {
                                device.turnOff();
                            } else {
                                throw new Exception("Invalid action: " + action);
                            }
                            System.out.println("Scheduled action executed: " + action + " for device " + device.getId() + " at " + time);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, delay, TimeUnit.MILLISECONDS);
    
                    System.out.println("Scheduled Task - [device: " + device.getDeviceType() + ", time: " + time + ", action: " + action + "]");
                } else {
                    throw new Exception("Scheduled time is in the past.");
                }
            } catch (ParseException e) {
                throw new Exception("Invalid time format. Please use HH:mm format.");
            }
        } else {
            throw new Exception("Device with ID " + deviceId + " not found.");
        }
    }
   

    // Method to add triggers
    public void addTrigger(String type, String operator, double threshold, String action) {
        triggers.add(new Trigger(type, operator, threshold, action));
    }

    // Method to check triggers and execute actions
    public void checkTriggers() {
        for (SmartDevice device : devices.values()) {
            if (device instanceof Thermostat) {
                double currentTemperature = ((Thermostat) device).getTemperature();
    
                for (Trigger trigger : triggers) {
                    if (trigger.getType().equals("temperature") && trigger.evaluate(currentTemperature)) {
                        // Execute the action defined in the trigger
                        executeAction(trigger.getAction());
                    }
                }
            }
        }
    }
    

    // Method to execute action from the trigger
    private void executeAction(String action) {
        String[] parts = action.split("\\(");
        String deviceAction = parts[0].trim();
        int deviceId = Integer.parseInt(parts[1].replace(")", "").trim());
        
        if (deviceAction.equals("turnOff")) {
            turnOffDevice(deviceId);
        } else if (deviceAction.equals("turnOn")) {
            turnOnDevice(deviceId);
        }
       
    }
    
    
}
