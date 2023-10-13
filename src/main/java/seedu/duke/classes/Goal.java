package seedu.duke.classes;

public class Goal {
    private String description;
    private int amount;

    public Goal(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
