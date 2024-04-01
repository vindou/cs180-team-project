import java.util.ArrayList;

/**
 * Database.java
 * 
 * An interface that lists the methods that the Database uses
 * 
 * @author Jack Juncker, Gilbert Chang, Sahil Shetty
 */
public interface Database {
    public ArrayList<Object> readDatabase();
    public boolean writeDatabase();
}
