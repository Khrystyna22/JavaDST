package rmi_client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import server_rmi.CommonInterface;

import static server_rmi.CommonInterface.USE_PORT;
import static server_rmi.CommonInterface.SERVER_NAME;

public class Run {

    private Interpretation interpretation;
    private Registry registry;
    private CommonInterface remoteCompute;

    public static boolean flag = true;

    public Run() throws Exception {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        registry = LocateRegistry.getRegistry(USE_PORT);
        remoteCompute = (CommonInterface) registry.lookup(SERVER_NAME);
        interpretation = new Interpretation(remoteCompute);
    }


    public void waiting() {

        System.out.println("Welcome to our.server");

        try(Scanner scan = new Scanner(System.in)) {
            while (flag)
                interpretation.interpretation(scan.nextLine());
        }
    }
}
