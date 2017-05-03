package team_f.client.pages.monthpublish;

public class Month {
    private String month;
    private int value;

    public Month(String month, int value) {
        this.month = month;
        this.value = value;
    }

    @Override
    public String toString() {
        return month.toString();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
