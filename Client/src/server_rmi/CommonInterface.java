package server_rmi;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Instant;

public interface CommonInterface extends Remote {

    public static final int USE_PORT = 12345;
    public static final String SERVER_NAME = "our.server";

    public String ping() throws RemoteException;

    public String echo(String text) throws RemoteException;

    public <T> T executeTask(CommonTask<T> t) throws IOException, RemoteException;

    public class SortFile implements CommonTask<FileInfo>, Serializable {

        private static final long serialVersionUID = 227L;

        private final CommonInterface.FileInfo fileInfo;

        public SortFile(FileInfo fileInfo) throws IOException {
            this.fileInfo = fileInfo;
        }

        @Override
        public CommonInterface.FileInfo execute() throws IOException {

            String data = new String(fileInfo.getFileContent(), StandardCharsets.UTF_8).trim();

            String[] dataMassString;
            if(data.equals("")){
                dataMassString = new String[]{};
            }else {
                dataMassString = data.split(" ");
            }

            int[] dataMassInt;
            if(dataMassString.length == 0){
                dataMassInt = new int[]{};
            } else {
                dataMassInt = new int[dataMassString.length];

                for (int i = 0; i < dataMassString.length; i++) {
                    dataMassInt[i] = Integer.parseInt(dataMassString[i]);
                }
            }

            int[] sortedMass = sortData(dataMassInt);

            File file = new File("sorted_" + fileInfo.getFilename());

            if (dataMassInt.length != 0) {
                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {

                    for (int i : sortedMass)
                        dos.writeBytes(i + ", ");

                } catch (Exception ex) {
                    System.out.println("Problem with write file");
                }
            }
            return new CommonInterface.FileInfo(file);
        }

        private  int[] sortData(int[] sortedMas){

            System.out.println("Start sort");
            int start = (int) (Instant.now().getEpochSecond());

            for (int i = 1; i < sortedMas.length; i++) {

                int index = i;
                int value = sortedMas[i];

                while (index > 0 && sortedMas[index - 1] > value) {
                    sortedMas[index] = sortedMas[index - 1];
                    index--;
                }
                sortedMas[index] = value;
            }

            int finish = (int) (Instant.now().getEpochSecond() - start);
            System.out.println("Algorithm worked " + finish + " seconds");

            return sortedMas;
        }
    }

    public static class FileInfo implements Serializable {

        private static final long serialVersionUID = 229L;
        private byte[] fileContent;
        private String filename;

        public FileInfo(File file) throws IOException {
            fileContent = Files.readAllBytes(file.toPath());
            filename = file.getName();
        }

        public String getFilename() {
            return filename;
        }

        public byte[] getFileContent() {
            return fileContent;
        }
    }
}
