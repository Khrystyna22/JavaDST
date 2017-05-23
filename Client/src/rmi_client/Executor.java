package rmi_client;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

import server_rmi.CommonInterface;
import server_rmi.CommonInterface.FileInfo;

import static server_rmi.CommonInterface.SortFile;

public class Executor {

    private final CommonInterface remoteCompute;

    private static final int UPPER_LIMIT = 1_000_000_000;
    private static final int SIZE = 1_000_000;


    public Executor(CommonInterface compute) {
        this.remoteCompute = compute;
    }

    public void exit() throws RemoteException {
        System.out.println("Client closed");
        Run.flag = false;
    }

    public void ping() throws RemoteException {
        System.out.println(remoteCompute.ping());
    }

    public void echo(String[] parsedParameters) throws RemoteException {
        if (parsedParameters.length != 2) {
            System.out.println("Bad argument!");
            return;
        }

        System.out.println(remoteCompute.echo(parsedParameters[1]));
    }

    public void sort(String[] parsedParameters) throws IOException {

        if (parsedParameters.length != 2) {
            System.out.println("Bad argument");
            return;
        }

        String[] valueInStringMass = new String[SIZE];
        for (int i = 0; i < valueInStringMass.length; i++)
            valueInStringMass[i] = String.valueOf((int) (Math.random() * UPPER_LIMIT));

        File file = fileWriter(parsedParameters[1], valueInStringMass);
        FileInfo newFileInfo = new FileInfo(file);

        FileInfo fileInfo = remoteCompute.executeTask(new SortFile(newFileInfo));
        System.out.println("Done!");

        String[] toServer = new String(fileInfo.getFileContent(), StandardCharsets.UTF_8).split(" ");

        fileWriter(fileInfo.getFilename(), toServer);
    }


    private File fileWriter(String name, String[] toServer) {

        File file = new File(name);
        try (DataOutputStream newStream = new DataOutputStream(new FileOutputStream(file))) {

            for (int i = 0; i < toServer.length; i++) {
                newStream.writeBytes(toServer[i] + " ");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }
}
