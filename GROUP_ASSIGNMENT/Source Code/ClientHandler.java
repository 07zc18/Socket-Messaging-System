import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientHandler extends Thread {

    // Socket connected to a specific client
    private Socket clientSocket;

    // Output stream used to send messages to this client
    private PrintWriter output;

    // Constructor
    public ClientHandler(Socket clientSocket) {

        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {

        try {

            // Create output stream
            output = new PrintWriter(
                    clientSocket.getOutputStream(),
                    true
            );

            // Create input stream
            BufferedReader input =
                    new BufferedReader(
                    new InputStreamReader(
                    clientSocket.getInputStream()));

            String message;

            // Read messages from the connected client
            while ((message = input.readLine()) != null) {

                System.out.println("Message: " + message);
                Logger.log("Message: " + message);

                // Disconnect if the client sends "exit"
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Forward the message to all other connected clients
                Server.broadcast(message, this);

            }

        } catch (IOException e) {

            System.out.println("Client disconnected");
            Logger.log("Client disconnected");

        } finally {

            // Remove the client from the connected client list
            Server.clientList.remove(this);

            try {

                // Close the client socket
                clientSocket.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

            System.out.println("Client removed");
            Logger.log("Client removed");

        }

    }

    // Send a message to this client
    public void sendMessage(String message) {

        output.println(message);

    }

}