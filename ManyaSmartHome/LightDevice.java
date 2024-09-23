package ManyaSmartHome;

public class LightDevice implements SmartDevice {
        private int id;
        private boolean isOn;
        
    
        public LightDevice(int id) {
            this.id = id;
            this.isOn = false;
        }
    
        @Override
        public void turnOn() {
            isOn = true;
            System.out.println("Light " + id + " is turned on.");
        }
    
        @Override
        public void turnOff() {
            isOn = false;
            System.out.println("Light " + id + " is turned off.");
        }
    
        @Override
        public String getStatus() {
            return "Light " + id + " is " + (isOn ? "On" : "Off");
        }
    
        @Override
        public int getId() {
            return id;
        }
    
        @Override
        public String getDeviceType() {
            return "Light";
        }
    
        @Override
        public void update(String status) {
            // Implementation for observer update (if necessary)
            System.out.println("Light " + id + " received update: " + status);
        }
        

       
    }
