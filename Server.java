import java.io.*;
import java.net.*;
import java.util.Scanner;

public interface Server {
    public static final Socket socket = new Socket();
    public void run() throws IOException;
    public void shutdown(); 
}
