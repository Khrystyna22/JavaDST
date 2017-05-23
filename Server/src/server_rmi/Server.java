package server_rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements CommonInterface {

    private Registry registry;
    private CommonInterface stub;

    public void runServer() {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            registry = LocateRegistry.createRegistry(USE_PORT);
            stub = (CommonInterface) UnicastRemoteObject.exportObject(this, USE_PORT);
            registry.rebind(CommonInterface.SERVER_NAME, stub);

        } catch (Exception ex) {
            System.err.println("Problem with run server");
        }
    }

    public void close(){
        try {
            if (registry != null) {
                registry.unbind(SERVER_NAME);
                registry = null;
            }

            if (stub != null) {
                UnicastRemoteObject.unexportObject(this, true);
                stub = null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public String ping() throws RemoteException {
        return "Ping successful";
    }

    @Override
    public String echo(String echo) throws RemoteException {
        return "Response :" + echo;
    }

    @Override
    public <T> T executeTask(CommonTask<T> t) throws IOException, RemoteException {
        return t.execute();
    }
}
