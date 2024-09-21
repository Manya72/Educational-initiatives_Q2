package ManyaSmartHome;
public interface SmartDevice extends SmartDeviceObserver {
    void turnOn();
    void turnOff();
    String getStatus();
    int getId();
    String getDeviceType();
    void update(String status);
}
