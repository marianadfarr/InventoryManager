package Model;
/**
 * Models InHouse Parts - a subclass of the abstract class Part.
 * @author Mariana Farr
 */
public class InHouse extends Part {

    private int machineId;


    public InHouse(int ID, String name, double price, int stock, int min, int max, int machineId) {
        super(ID, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * @return the Machine ID
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * @param machineId the Machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
