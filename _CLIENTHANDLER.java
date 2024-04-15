import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class _CLIENTHANDLER implements Runnable {
    private _SERVERCLASS accessedServer;
    private Socket clientSocket;
    private User assocUser;

    public _CLIENTHANDLER(_SERVERCLASS accessedServer, Socket clientSocket) {
        this.accessedServer = accessedServer;
        this.clientSocket = clientSocket;
        this.assocUser = null;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

            writer.println("What would you like to do: \n1) Log In\n2) Create New Accountend");
            writer.flush();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}