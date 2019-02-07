import java.lang.Thread;
import java.io.*;

public class P1c extends Thread {

    static private int N;
    private int[][] result;
    private int[][] a, b;
    private Thread[] threads;

    private class ProductoFila extends Thread {

        private int i;

        public ProductoFila(int i) {
            this.i = i;
        }

        public void run() {
           int j,c;
           for (j=0; j < N; j++) {
               for (result[i][j] = c=0; c < N; c++) {
                   result[i][j] += a[this.i][c]*b[c][j];    
               }
           }
        }
    }

    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void run() {
        threads = new Thread[N];
        a = new int[N][N];
        b = new int[N][N];
        result = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = i;
                b[i][j] = j;
            }
        }
        System.out.println("A: "); printMatrix(a);
        System.out.println("B: "); printMatrix(b);
        for (int i = 0; i < N; i++) {
            threads[i] = new ProductoFila(i);
            threads[i].start();
        }
        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                
            }
            
        }
        System.out.println("Result: ");  printMatrix(result);
    }

    public static void main(String args[]) {
        N = Integer.parseInt(System.getProperty("N"));
        Thread p1 = new P1c();
        p1.start();
        try {
            p1.join();
        } catch (InterruptedException e) {
            return;
        }
        
    }
}

