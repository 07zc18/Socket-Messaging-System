import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String FILE_NAME = "server.log";

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static synchronized void log(String message) {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_NAME, true))) {

            String timestamp =
                    LocalDateTime.now().format(formatter);

            writer.println("[" + timestamp + "] " + message);

        } catch (IOException e) {

            System.out.println("Logging failed.");

        }

    }

}