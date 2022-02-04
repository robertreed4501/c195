package Model;

/**This class defines reportData objects for the apptsByTypeAndMonth table. */
public class ReportData {

    /**The appointment type. */
    private String type;

    /**the number (count) of that type of appointment. */
    private int count;

    /**Constructor for reportData objects. */
    public ReportData(String type, int count) {
        this.type = type;
        this.count = count;
    }

    /**This method increases the count by 1. */
    public void increment(){
        this.count++;
    }

    /**This method gets the type.
     *
     * @return the type*/
    public String getType() {
        return type;
    }

    /**This method sets the type.
     *
     * @param type the type*/
    public void setType(String type) {
        this.type = type;
    }

    /**This method gets the count.
     *
     * @return the count*/
    public int getCount() {
        return count;
    }

    /**This method sets the count.
     *
     * @param count the count*/
    public void setCount(int count) {
        this.count = count;
    }
}
