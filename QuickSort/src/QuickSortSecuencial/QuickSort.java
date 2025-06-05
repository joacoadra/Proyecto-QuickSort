package QuickSortSecuencial;
import java.util.Random;


public class QuickSort {

    // Método para dividir el arreglo usando un pivote
    int partition(int a[], int low, int high) {
        // Elegimos el último elemento como pivote
        int pivot = a[high];

        // i es el índice del elemento más pequeño (que está en su lugar correcto)
        int i = (low - 1);

        // Recorremos desde low hasta high - 1
        for (int j = low; j < high; j++) {
            // Si el elemento actual es menor o igual al pivote
            if (a[j] <= pivot) {
                i++;

                // Intercambiamos a[i] con a[j]
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        // Colocamos el pivote en su posición correcta (después de los menores)
        int temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        // Devolvemos la posición del pivote
        return i + 1;
    }

    // Método principal de QuickSort (recursivo)
    void sort(int a[], int l, int h) {
        // Si hay más de un elemento
        if (l < h) {
            // Particionamos el arreglo y obtenemos la posición del pivote
            int pi = partition(a, l, h);

            // Ordenamos recursivamente los elementos antes y después del pivote
            sort(a, l, pi - 1);
            sort(a, pi + 1, h);
        }
    }

    // Método principal del programa
    public static void main(String args[]) {
        // Arreglo de entrada
        int a[] = new int[1000];
        for (int i = 0; i<1000; i++) {
        	a[i] = new Random().nextInt(100);
        }
        int n = a.length;

        // Creamos una instancia de la clase QuickSort
        QuickSort ob = new QuickSort();

        // Llamamos al método sort para ordenar el arreglo completo
        ob.sort(a, 0, n - 1);
        
  
        // Mostramos el arreglo ya ordenado
        for (int i = 0; i < n; ++i) {
            System.out.print(a[i] + " ");
        }
      
    }
}
