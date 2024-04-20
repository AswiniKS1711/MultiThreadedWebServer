import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    public Runnable getRunnable() {
        // Here, I didn't use lambda
        // instead, I used the anonymous class

        return new Runnable() {

            @Override
            public void run() {
                int port = 8010;

                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);

                    try (
                            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader fromServer = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));) {
                        toServer.println("Hello from Client " + socket.getLocalSocketAddress());
                        String line = fromServer.readLine();
                        System.out.println("Response from server " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String args[]) {
        Client client = new Client();

        // Creating 100 clients
        for (int i = 0; i < 100; i++) {
            try {
                Thread thread = new Thread(client.getRunnable());
                // Runnable is a functional interface, which does nothing but calls run()

                thread.start(); // this will start requesting the server
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
