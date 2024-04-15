import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * This class represents a Server interface for Server classes.
 *
 *
 *
 * @author Jack Juncker, Ellie Williams
 * @version April 2nd, 2024.
 */

public interface Server {
    public static final Socket SOCKET = new Socket();
    public void run();
    public void shutdown();
}