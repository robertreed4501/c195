package Model;

public class ReportData {
    private String type;
    private int count;

    public ReportData(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public void increment(){
        this.count++;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
