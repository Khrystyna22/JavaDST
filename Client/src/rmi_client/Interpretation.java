package rmi_client;

import server_rmi.CommonInterface;

public class Interpretation {
    private final Executor executor;
    
    public Interpretation(CommonInterface remoteCompute) {
        executor = new Executor(remoteCompute);
    }

    private String[] pars(String inLine){
        String[] parsed = inLine.split(" ", 2);

        if(parsed[0].equals("echo"))
            return parsed;
        else
            return inLine.split(" ");
    }

    public void interpretation(String inLine) {

        try {
            String[] parsed = pars(inLine);

            switch (parsed[0]) {

                case "ping":
                    executor.ping();
                    break;

                case "echo":
                    executor.echo(parsed);
                    break;

                case "sort":
                    executor.sort(parsed);
                    break;

                case "exit":
                    executor.exit();
                    break;

                default:
                    System.out.println("Incorrect command");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
