package seedu.duke.classes;

public class TypePrint {
    private String description;
    private double currentAmount;
    private double targetAmount;

    public TypePrint(String description, double currentAmount) {
        this(description, currentAmount, 0.0);
    }

    public TypePrint(String description, double currentAmount, double targetAmount) {
        this.description = description;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
    }

    public String getDescription() {
        return description;
    }

    public boolean targetAmountExists() {
        return targetAmount > 0.0;
    }

    public String getCurrentAmount() {
        return String.format("%.2f", currentAmount);
    }

    public String getTargetAmount() {
        return String.format("%.2f", targetAmount);
    }

    public String getAmount() {
        if (targetAmount == 0.0) {
            return getCurrentAmount();
        } else {
            return getCurrentAmount() + "/" + getTargetAmount();
        }
    }

    public Double getPercentage() {
        if (targetAmount == 0.0) {
            return null;
        } else {
            return currentAmount / targetAmount * 100;
        }

    }
}
