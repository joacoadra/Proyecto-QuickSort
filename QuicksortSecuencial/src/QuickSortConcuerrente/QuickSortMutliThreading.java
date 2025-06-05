package QuickSortConcuerrente;

// Importación de clases necesarias
import java.io.*;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Clase principal que extiende RecursiveTask (permite ejecutar en paralelo y devolver un resultado)
public class QuickSortMutliThreading extends RecursiveTask<Integer> {

    // Índices de inicio y fin del subarreglo a ordenar
    int start, end;
    // Arreglo a ordenar
    int[] arr;

    /**
     * Método que realiza la partición del arreglo con un pivote aleatorio
     */
    private int partition(int start, int end, int[] arr) {
        int i = start, j = end;

        // Elegimos un pivote aleatorio dentro del rango [start, end)
        int pivoted = new Random().nextInt(j - i) + i;

        // Intercambiamos el pivote con el último elemento
        int t = arr[j];
        arr[j] = arr[pivoted];
        arr[pivoted] = t;
        j--;

        // Iniciamos el proceso de partición
        while (i <= j) {
            // Si el valor actual es menor o igual al pivote, lo dejamos en su lugar
            if (arr[i] <= arr[end]) {
                i++;
                continue;
            }

            // Si el valor en j es mayor o igual al pivote, lo dejamos en su lugar
            if (arr[j] >= arr[end]) {
                j--;
                continue;
            }

            // Intercambiamos los elementos que están en el lado incorrecto del pivote
            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }

        // Colocamos el pivote en su posición final
        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;

        // Devolvemos la posición del pivote
        return j + 1;
    }

    // Constructor que recibe los parámetros necesarios para la tarea
    public QuickSortMutliThreading(int start, int end, int[] arr) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    // Método que se ejecuta al correr la tarea (QuickSort recursivo)
    @Override
    protected Integer compute() {
        // Caso base: si el subarreglo es de un solo elemento o vacío, no hacemos nada
        if (start >= end)
            return null;

        // Realizamos la partición y obtenemos la posición del pivote
        int p = partition(start, end, arr);

        // Creamos tareas para ordenar las dos mitades
        QuickSortMutliThreading left = new QuickSortMutliThreading(start, p - 1, arr);
        QuickSortMutliThreading right = new QuickSortMutliThreading(p + 1, end, arr);

        // Ejecutamos la parte izquierda en un hilo separado
        left.fork();

        // Ejecutamos la parte derecha en el hilo actual
        right.compute();

        // Esperamos a que termine la parte izquierda
        left.join();

        // No se devuelve ningún valor
        return null;
    }

    // Método principal
    public static void main(String args[]) {
        int arr[] = new int[1000];
        for (int i = 0; i<1000; i++) {
        	arr[i] = new Random().nextInt(100);
        }
        int n = arr.length;
        
    
        // Creamos un pool de hilos basado en ForkJoin
        ForkJoinPool pool = ForkJoinPool.commonPool();

        // Iniciamos la tarea principal para ordenar el arreglo completo
        pool.invoke(new QuickSortMutliThreading(0, n - 1, arr));

       // Mostramos el arreglo ordenado
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }

    }
}
