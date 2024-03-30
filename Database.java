import java.util.ArrayList;

public interface Database {
    public ArrayList<Object> readDatabase(String fileName);
    public boolean writeDatabase(); 
}
