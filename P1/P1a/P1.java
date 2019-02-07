import java.lang.Thread;
import java.io.*;

public class P1 extends Thread {

    static private int N;
    static private int T;

    private class Sleeper extends Thread {
        public void run() {
            try {
                System.out.println(this.getId());
                Thread.sleep(T);
                System.out.println(this.getId());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void run() {
        System.out.println("Main thread: " + this.getId());
        for (int i = 0; i < N; i++) {
            (new Sleeper()).start();
        }
    }

    public static void main(String args[]) {
        N = Integer.parseInt(System.getProperty("N"));
        T = Integer.parseInt(System.getProperty("T"));
        Thread p1 = new P1();
        p1.start();
        try {
            p1.join(); 
        } catch (InterruptedException e) {
            return;
        }
        
    }
}