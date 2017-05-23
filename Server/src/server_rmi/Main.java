package server_rmi;

import java.io.IOException;
import java.util.Scanner;

import static server_rmi.CommonInterface.USE_PORT;
import static server_rmi.CommonInterface.SERVER_NAME;

public class Main{

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer();

        System.out.println("Server starting on default address and port : " + SERVER_NAME + " " + USE_PORT);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        server.close();

        System.out.println("Server is closed");
    }
}
