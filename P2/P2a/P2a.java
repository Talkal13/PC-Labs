
import java.lang.Thread;
import java.io.*;

public class P2a extends Thread {

    static private int N = 0;
    volatile private boolean a;
    volatile private int r;

    private class Incrementer extends Thread {

        public void run() {
           for (int i = 0; i < N; i++) {
               while(a);
               r = r + 1;
               a = true;
           }
        }
    }

    private class Decrementer extends Thread {

        public void run() {
           for (int i = 0; i < N; i++) {
                while(!a);
                r = r - 1;
                a = false;
           }
        }
    }


    public void run() {
        a = false;
        r = 0;
        Thread incrementer = new Incrementer();
        Thread decrementer = new Decrementer();
        try {
            incrementer.start();
            decrementer.start();
            incrementer.join();
            decrementer.join();
        } catch (InterruptedException e) {
            
        }
        
        System.out.println("Result = " + r);
    }

    public static void main(String args[]) {
        N = Integer.parseInt(System.getProperty("N", "0"));
        Thread p1 = new P2a();
        p1.start();
        try {
            p1.join();
        } catch (InterruptedException e) {
            return;
        }
        
    }
}


