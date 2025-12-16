import java.util.*;

public class HeapXML {
    private LinkedList<NodoXML> elementos;
    private Boolean esMaxHeap;

    public HeapXML(Boolean esMaxHeap) {
        this.elementos = new LinkedList<>();
        this.esMaxHeap = esMaxHeap;
    }

    public void insertar(NodoXML nodo) {
        elementos.add(nodo);
        heapifyArriba(elementos.size() - 1);
    }

    private void heapifyArriba(int indice) {
        if (indice == 0) return;
        int padre = (indice - 1) / 2;
        if (comparar(elementos.get(indice), elementos.get(padre))) {
            intercambiar(indice, padre);
            heapifyArriba(padre);
        }
    }

    private void heapifyAbajo(int indice) {
        int izq = 2 * indice + 1;
        int der = 2 * indice + 2;
        int mayor = indice;
        if (izq < elementos.size() && comparar(elementos.get(izq), elementos.get(mayor))) {
            mayor = izq;
        }
        if (der < elementos.size() && comparar(elementos.get(der), elementos.get(mayor))) {
            mayor = der;
        }
        if (mayor != indice) {
            intercambiar(indice, mayor);
            heapifyAbajo(mayor);
        }
    }

    private Boolean comparar(NodoXML n1, NodoXML n2) {
        if (n1.getValor() == null || n2.getValor() == null) return false;
        int comp = n1.getValor().compareTo(n2.getValor());
        if (esMaxHeap) {
            return comp > 0;
        } else {
            return comp < 0;
        }
    }

    private void intercambiar(int i, int j) {
        NodoXML temp = elementos.get(i);
        elementos.set(i, elementos.get(j));
        elementos.set(j, temp);
    }

    public NodoXML extraer() {
        if (elementos.isEmpty()) return null;
        NodoXML raiz = elementos.get(0);
        NodoXML ultimo = elementos.removeLast();
        if (!elementos.isEmpty()) {
            elementos.set(0, ultimo);
            heapifyAbajo(0);
        }
        return raiz;
    }

    public NodoXML peek() {
        if (elementos.isEmpty()) return null;
        return elementos.get(0);
    }

    public Boolean isEmpty() {
        return elementos.isEmpty();
    }

    public int size() {
        return elementos.size();
    }

    public void imprimir() {
        for (NodoXML n : elementos) {
            if (n.getValor() != null) {
                System.out.println(n.getNombre() + ": " + n.getValor());
            } else {
                System.out.println(n.getNombre());
            }
        }
    }

    public LinkedList<NodoXML> ordenar(LinkedList<NodoXML> lista, Boolean maxHeap) {
        HeapXML h = new HeapXML(maxHeap);
        for (NodoXML n : lista) {
            h.insertar(n);
        }
        LinkedList<NodoXML> resultado = new LinkedList<>();
        while (!h.isEmpty()) {
            resultado.add(h.extraer());
        }
        return resultado;
    }
}
