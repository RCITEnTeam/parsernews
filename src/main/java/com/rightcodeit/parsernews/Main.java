package com.rightcodeit.parsernews;

public class Main {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Controller c = new Controller();
                c.start();
            }
        };
        r.run();

        // write your code here
    }
}