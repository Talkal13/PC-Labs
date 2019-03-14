import java.lang.Thread;
import java.io.*;
import java.util.concurrent.Semaphore;

public class P3a extends Thread {

    private final int N = Integer.parseInt(System.getProperty("N", "10"));
    volatile private int r;
    private final Semaphore sem = new Semaphore(1);

    private class Incrementer extends Thread {

        public void run() {
           for (int i = 0; i < N; i++) {
               try {
                sem.acquire();
                r = r + 1;
                System.out.println("Increment: " + r);
                sem.release();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
           }
        }
    }

    private class Decrementer extends Thread {

        public void run() {
           for (int i = 0; i < N; i++) {
               try {
                sem.acquire();
                r = r - 1;
                System.out.println("Decrement: " + r);
                sem.release();
               } catch(InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }


    public void run() {
        r = 0;
        Thread incrementer = new Incrementer();
        Thread decrementer = new Decrementer();
        try {
            incrementer.start();
            decrementer.start();
            incrementer.join();
            decrementer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Result = " + r);
    }

    public static void main(String args[]) {
        Thread p1 = new P3a();
        try {
            p1.start();
            p1.join();
        } catch (InterruptedException e) {
            return;
        }
        
    }
}