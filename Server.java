import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    // functional interface
    public Consumer<Socket> getConsumer() {
        // this lamba function is overriding the accept() of Consumer interface
        // alternative,

        /*
         * return new Consumer<Socket>() {
         * 
         * @Override
         * public void accept(Socket clientSocket)
         * {
         * ......
         * ......
         * }
         * }
         */

        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");

                toClient.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String args[]) {
        int port = 8010;

        Server server = new Server();

        try {

            ServerSocket serverSocket = new ServerSocket(port); // created a socket on the given port

            serverSocket.setSoTimeout(10000);

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket acceptedSocket = serverSocket.accept();

                // After connection is accepted, unlike Single threaded web server, we dont need
                // to send any data to client,
                // instead, we are creating a new thread
                // And, we will put the acceptedSocket (via which connection is established) in
                // the thread

                // The thread needs to run a function now
                // So, that function will define the communication with the client using socket
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}