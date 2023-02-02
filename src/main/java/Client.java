import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket(ServerConfig.HOST, ServerConfig.PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println(reader.readLine());
            writer.println(new Scanner(System.in)
                    .nextLine());
            StringBuilder stringBuilder = new StringBuilder();
            String result;
            while ((result = reader.readLine()) != null) {
                stringBuilder.append(result);
                System.out.println(result);
            }
        } catch (IOException e) {
            System.out.println("Не могу запустить клиента");
            e.printStackTrace();
        }
    }
}