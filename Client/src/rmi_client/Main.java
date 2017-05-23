package rmi_client;

public class Main {
    
    public static void main(String[] args) {        
        try {            
            Run run = new Run();
            run.waiting();
        } catch (Exception ex) {
            ex.printStackTrace();
        }      
    }
}
