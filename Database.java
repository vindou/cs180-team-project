import java.util.ArrayList;

public interface Database {
    public ArrayList<Object> readDatabase();
    public boolean writeDatabase(); 
}
