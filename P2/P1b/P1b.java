import java.lang.Thread;
import java.io.*;

public class P1b extends Thread {

    static private int N, M;
    private int n;
    private Thread[] adder;
    private Thread[] subs;

    private class Incrementer extends Thread {
        public void run() {
           for (int i = 0; i < N; i++) {
               n = n + 1;
           }
        }
    }

    private class Decrementer extends Thread {
        public void run() {
            for (int i = 0; i < N; i++) {
                n = n - 1;
            }
            
        }
    }

    public void run() {
        adder = new Thread[M];
        subs = new Thread[M];
        n = 0;
        System.out.println("Result: " + n);
        for (int i = 0; i < M; i++) {
            adder[i] = new Incrementer();
            subs[i] = new Decrementer();
            adder[i].start();
            subs[i].start();
        }
        for (int i = 0; i < M; i++) {
            try {
                adder[i].join();
                subs[i].join();
            } catch (InterruptedException e) {
                
            }
            
        }
        System.out.println("Result: " + n);
    }

    public static void main(String args[]) {
        N = Integer.parseInt(System.getProperty("N"));
        M = Integer.parseInt(System.getProperty("M"));
        Thread p1 = new P1b();
        
        p1.start();
        try {
            p1.join();
        } catch (InterruptedException e) {
            return;
        }
        
    }
}

