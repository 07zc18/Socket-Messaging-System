import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {

            // Server IP address and port number
            Socket socket = new Socket("127.0.0.1", 5000);

            System.out.println("Connected to server");

            // Output stream for sending messages to the server
            PrintWriter output =
                    new PrintWriter(
                    socket.getOutputStream(),
                    true);

            // Input stream for receiving messages from the server
            BufferedReader input =
                    new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));

            // Create a separate thread to continuously receive messages
            Thread receiveThread = new Thread(() -> {

                try {

                    String message;

                    // Read messages sent by the server
                    while ((message = input.readLine()) != null) {

                    	// Display received message from other client
                        System.out.println("\nReceived: " + message);
                        System.out.print("Enter message: ");

                    }

                } catch (IOException e) {

                    System.out.println("Disconnected");

                }

            });

            // Start the receiving thread
            receiveThread.start();

            // Scanner for user input
            Scanner scanner = new Scanner(System.in);

            // Continuously allow the user to send messages
            while (true) {

                System.out.print("Enter message: ");

                String message = scanner.nextLine();

                // Send the message to the server
                output.println(message);

                // Disconnect if the user types "exit"
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            // Close the client socket
            socket.close();

            System.out.println("Disconnected from server");

        } catch (IOException e) {

            System.out.println("Unable to connect");

        }

    }

}