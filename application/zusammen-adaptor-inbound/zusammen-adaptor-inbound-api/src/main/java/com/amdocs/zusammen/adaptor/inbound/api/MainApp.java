package com.amdocs.zusammen.adaptor.inbound.api;

/**
 * Version supplier
 */
public class MainApp {

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        String version = mainApp.getClass().getPackage().getImplementationVersion();
        System.out.println("Jar version is "+version);
        throw new UnsupportedOperationException("");
     }
}
