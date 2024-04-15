import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * This class outlines an interface for
 * Client class objects
 *
 *
 *
 * @author Ellie Williams, Jack Juncker
 * @version April 1st, 2024.
 */

public interface Client {
    public static final Socket SOCKET = new Socket();

    public void run();
    public void stop(); 
}
