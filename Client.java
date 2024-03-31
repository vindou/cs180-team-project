import java.io.*;
import java.net.*;
import java.util.Scanner;

public interface Client {
    public static final Socket socket = new Socket();

    public void run();
    public void stop(); 
}
