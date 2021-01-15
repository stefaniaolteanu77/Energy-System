package output;

import java.util.ArrayList;
import java.util.List;

public class MonthlyStats {
    private int month;
    private List<Integer> distributorsId;

    public MonthlyStats(int month) {
        this.month = month;
        distributorsId = new ArrayList<>();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsId() {
        return distributorsId;
    }

    public void setDistributorsId(List<Integer> distributorsId) {
        this.distributorsId = distributorsId;
    }

    @Override
    public String toString() {
        return "MonthlyStats{" +
                "month=" + month +
                ", distributorsId=" + distributorsId +
                '}';
    }
}
