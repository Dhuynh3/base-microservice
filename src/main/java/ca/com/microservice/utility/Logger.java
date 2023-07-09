package ca.com.microservice.utility;


public class Logger {
    
    public Logger() {

    }

    public void info(String log) {
        System.out.println("[Info] : " + log);
    }

    public void warning(String log) {
        System.out.println("[Warning] : " + log);
    }

    public void severe(String log) {
        System.out.println("[Severe] : " + log);
    }

}
