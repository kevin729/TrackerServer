package com.professorperson.tracker.time;

import com.professorperson.tracker.models.Message;
import com.professorperson.tracker.web.I_WebSocket;

public class Timer implements Runnable {

    private Thread thread = new Thread(this);
    private boolean running = false;

    private int seconds = 0;
    private String to;

    private I_WebSocket socket;

    public Timer(I_WebSocket socket, String to) {
        this.socket = socket;
        this.to = to;
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

                Message message = new Message();
                message.setText(Integer.toString(seconds));
                message.setTo(to);

                socket.send(message, "/app/timer");
            }

            lastTime = now;
        }
    }
}
