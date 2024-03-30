import java.util.ArrayList;

public interface Database {
    public ArrayList<Object> readDatabase(Object o);
    public boolean writeDatabase(); 
}
