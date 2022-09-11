package com.professorperson.tracker.time;

import com.professorperson.tracker.web.I_WebSocket;

public class Timer implements Runnable {

    private Thread thread = new Thread(this);
    private boolean running = false;

    private int seconds = 0;

    private I_WebSocket socket;

    public Timer(I_WebSocket socket) {
        this.socket = socket;
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double time_per_frame = 1000000000;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime)/time_per_frame;

            if (delta >= 1) {
                delta = 0;
                seconds++;
                socket.send(Integer.toString(seconds), "/app/timer");
            }

            lastTime = now;
        }
    }
}
