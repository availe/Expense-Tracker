package JavaFX;

public class ExpenseRecord {
    private double amount;
    private String category;
    private String date;
    private String department;
    private String description;
    private Integer expenseID;

    public ExpenseRecord(Integer expenseId, Double amount, String category, String date, String department, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.department = department;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
    }
}
