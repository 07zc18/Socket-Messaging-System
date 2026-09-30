import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;

public class Server {

    // Store all currently connected clients
    public static ArrayList<ClientHandler> clientList = new ArrayList<>();

    public static void main(String[] args) {

        // Server port number
        int port = 5000;

        try {

            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started...");
            Logger.log("Server started on port " + port);
            System.out.println("Waiting for clients...");

            // Continuously accept client connections
            while (true) {

                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected");
                Logger.log("Client connected");

                // Create a new thread to handle this client
                ClientHandler handler = new ClientHandler(clientSocket);

                // Add the client handler to the list of connected clients
                clientList.add(handler);

                // Start the client thread
                handler.start();

            }

        } catch (IOException e) {

            System.out.println("Server error: " + e.getMessage());
            Logger.log("Server error: " + e.getMessage()); 

        }
    }

    // Broadcast a message to all connected clients except the sender
    public static void broadcast(String message, ClientHandler sender) {

        for (ClientHandler client : clientList) {

            // Prevent sending the message back to the sender
            if (client != sender) {

                client.sendMessage(message);

            }

        }

    }

}