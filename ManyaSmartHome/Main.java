package ManyaSmartHome;

public class Main {
    public static void main(String[] args) throws Exception {
        SmartHomeSystem homeSystem = new SmartHomeSystem();
        
        // Create device factory instance
        SmartDeviceFactory deviceFactory = SmartDeviceFactory.getInstance();
        
        // Create devices
        SmartDevice light = deviceFactory.getInstance("Light", 101);
        SmartDevice thermostat = deviceFactory.getInstance("Thermostat", 102); // Assuming temperature is passed in constructor

        // Add devices to the system
        homeSystem.addDevice(light);
        homeSystem.addDevice(thermostat);
        
        // Report initial status of devices
        System.out.println("Initial Status:");
        homeSystem.reportStatus();
        
        // Schedule actions
        try {
            homeSystem.setSchedule(101, "16:55", "on");
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        homeSystem.setSchedule(102, "19:00", "off");

        // Add triggers
        // homeSystem.addTrigger("temperature", ">", 75, "turnOff(101)"); // Example trigger for temperature
        // homeSystem.addTrigger("temperature", "<", 60, "turnOn(101)");

        // // Simulate temperature changes and check triggers
        // System.out.println("\nChecking triggers at 80 degrees:");
        // ((Thermostat) thermostat).setTemperature(80);
        // homeSystem.checkTriggers(); // Should turn off the light
        
        // System.out.println("\nChecking triggers at 50 degrees:");
        // ((Thermostat) thermostat).setTemperature(50);
        // homeSystem.checkTriggers(); // Should turn on the light

        // // Remove a device
        // homeSystem.removeDevice(101); // Remove the light
        // System.out.println("\nAfter removing the light:");
        // homeSystem.reportStatus();
        
        // // Attempt to remove a non-existing device
        // homeSystem.removeDevice(101); // Should indicate not found

        // Shutdown scheduler if needed
        
    }
}
