package edu.escuelaing.arep.designprimer;

import java.io.IOException;

public class ThreadRequester extends Thread{

    private final Request request;

    public ThreadRequester(Request request) {
        this.request = request;
    }

    @Override
    public void run(){
        try {
            HttpConnection.getStock(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
