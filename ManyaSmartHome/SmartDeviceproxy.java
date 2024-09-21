package ManyaSmartHome;

class DeviceProxy  {
    private SmartDevice device;
    private String userRole; // Example user role

    public DeviceProxy(SmartDevice device, String userRole) {
        this.device = device;
        this.userRole = userRole;
    }


    public void turnOn() {
        if (!hasAccess()) {
            System.out.println("Access Denied: You do not have permission to turn on the device.");
            return;
        }
        System.out.println("Accessing Device: Turning on...");
        device.turnOn();
        System.out.println("Device is now On.");
    }


    public void turnOff() {
        if (!hasAccess()) {
            System.out.println("Access Denied: You do not have permission to turn off the device.");
            return;
        }
        System.out.println("Accessing Device: Turning off...");
        device.turnOff();
        System.out.println("Device is now Off.");
    }

    private boolean hasAccess() {
        // Simple permission check based on user role
        return "admin".equals(userRole); // Only admins can control the device
    }


    public String getStatus() {
        return device.getStatus();
    }
}
