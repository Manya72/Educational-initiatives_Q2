package ManyaSmartHome;

public class Trigger {
    private String type;
    private String operator;
    private double threshold;
    private String action;

    public Trigger(String type, String operator, double threshold, String action) {
        this.type = type;
        this.operator = operator;
        this.threshold = threshold;
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public String getOperator() {
        return operator;
    }

    public double getThreshold() {
        return threshold;
    }

    public String getAction() {
        return action;
    }

    public boolean evaluate(double currentValue) {
        switch (operator) {
            case ">":
                return currentValue > threshold;
            case "<":
                return currentValue < threshold;
            case "==":
                return currentValue == threshold;
            default:
                return false;
        }
    }
}
