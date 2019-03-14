


import java.lang.Thread;
import java.io.*;
import java.util.concurrent.Semaphore;



/*
public interface Almacen {
    /**
    * Almacena (como ultimo) un producto en el almac ́en. Si no hay
    * hueco el proceso que ejecute el m ́etodo bloquear ́a hasta que lo
    *  haya.
    *
    public void almacenar(Producto producto);
    /**
    * Extrae el primer producto disponible. Si no hay productos el
    * proceso que ejecute el m ́etodo bloquear ́a hasta que se almacene un
    *  dato.
    *
    public Producto extraer();
}*/

public class P3b extends Thread {

    private final int N = Integer.parseInt(System.getProperty("N", "10"));

    private Producto almacenado;
    private final Semaphore empty = new Semaphore(1);
    private final Semaphore full = new Semaphore(1);

    public void almacenar(Producto producto) {
        try {

            empty.acquire();
            almacenado = producto;
            System.out.println("Store product: " + producto.id + " : " + producto.thread);
            full.release();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    
    }

    public Producto extraer() {
        try {
        full.acquire();
        Producto p = almacenado;
        System.out.println("Fetch product: " + p.id + " : " + p.thread);
        empty.release();
        return p;
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return almacenado;    
    }

    private class Producer extends Thread {
        int i = 0;
        public void run() {
            for (int j = 0; j < N; j++) {
                i = i+1;
                Producto p = new Producto(i, this.getId());
                almacenar(p);
            }

        }
    }

    public class Producto {
        public int id;
        public int thread;
        public Producto(int id, long thread) {
            id = id;
            thread = thread;
        }
    }

    private class Consumer extends Thread {
        int i = 0;
        public void run() {
            for (int j = 0; j < N; j++) {
            i = i+1; 
            Producto p = extraer();
            }
        }
    }


    public void run() {
        Thread producer = new Producer();
        Thread consumer = new Consumer();
        try {
            producer.start();
            consumer.start();
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String args[]) {
        Thread p1 = new P3b();
        try {
            p1.start();
            p1.join();
        } catch (InterruptedException e) {
            return;
        }
        
    }
}


