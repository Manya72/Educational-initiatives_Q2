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
        System.out.println();
        System.out.println("Initial Status:");
        homeSystem.reportStatus();
        
        // Create proxies for user roles
        DeviceProxy adminLightProxy = new DeviceProxy(light, "admin");
        DeviceProxy userLightProxy = new DeviceProxy(light, "user"); // Example of a non-admin role
      
        // Schedule actions
        try {
            homeSystem.setSchedule(101, "10:55", "on");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      

        // Add triggers
        homeSystem.addTrigger("temperature", ">", 75, "turnOff(101)"); // Example trigger for temperature
       System.out.println();
        // Simulate temperature changes and check triggers
        System.out.println("\nChecking triggers at 80 degrees:");
        ((Thermostat) thermostat).setTemperature(80);
        homeSystem.checkTriggers(); // Should turn off the light
        
        // Access control demonstration
        adminLightProxy.turnOn(); // Should succeed
        userLightProxy.turnOn(); // Should fail due to access control


        homeSystem.removeDevice(101); // Remove the light
        System.out.println("\nAfter removing the light:");
        homeSystem.reportStatus();
        
        // Attempt to remove a non-existing device
        homeSystem.removeDevice(101); // Should indicate not found
    }
}
