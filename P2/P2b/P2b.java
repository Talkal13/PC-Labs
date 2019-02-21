
import java.lang.Thread;
import java.io.*;

public class P2b extends Thread {

    static private int N = 0;
    static private int M = 0;
    //Lock Rompe Empate
    private volatile int[] in;
    private volatile int[] last;
    volatile private int r;

    private class LockRompeEmpate extends Thread {

        private boolean op;
        private int id;

        public LockRompeEmpate(int id) {
            this.op = id % 2 == 0;
            this.id = id;
        }

        public void run() {
           for (int i = 0; i < N; i++) { //Entry
               for (int j = 1; j <= M*2; j++) {
                   in[id] = j;
                   in = in;
                   last[j] = id;
                   last = last;
                   for (int k = 1; k <= M*2; k++) {
                       if (k != id)
                            while(in[k] >= in[id] && last[j] == id);
                   }
                       
               }
               r = (op) ? r + 1 : r - 1; //
               System.out.println("Thread id " + id + ", result = " + r);
               in[id] = 0; //Exit
               in = in;
                
           }
        }
    }


    public void run() {
        r = 0;
        in = new int[M*2 + 1];
        in[0] = 0;
        last = new int[M*2 + 1];
        last[0] = 0;
        Thread[] thread = new Thread[M*2 + 1];
        try {
            for (int i = 1; i <= M*2; i++) {
                in[i] = 0;
                last[i] = 0;
                thread[i] = new LockRompeEmpate(i);
                thread[i].start();
            }
        
            for (int i = 1; i <= M*2; i++) {
                thread[i].join();
            }
        } catch (InterruptedException e) {
            
        }
        
        System.out.println("Result = " + r);
    }

    public static void main(String args[]) {
        N = Integer.parseInt(System.getProperty("N", "0"));
        M = Integer.parseInt(System.getProperty("M", "0"));
        Thread p1 = new P2b();
        p1.start();
        try {
            p1.join();
        } catch (InterruptedException e) {
            return;
        }
        
    }
}


