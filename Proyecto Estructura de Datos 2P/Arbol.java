import java.util.*;

public class Arbol <E> {
    private NodoXML root;

    public Arbol() {}

    public void setRoot(NodoXML root){this.root = root;}
    public NodoXML getRoot(){return root;}
    public Boolean isEmpty(){if (root == null){return true;} else {return false;}}

    public void imprimirPreorden(String s) {
        if (root == null) return;
        if (root.getValor() == null) {
            System.out.println(s + root.getNombre());
        } else {
            System.out.println(s + root.getNombre() + ": " + root.getValor());}
        for (NodoXML hijo : root.hijos) {
            Arbol a = new Arbol();
            a.setRoot(hijo);
            a.imprimirPreorden(" "+ s);
        }
    }
    public void imprimirPostorden(String s) {
        if (root == null) return;
        for (NodoXML hijo : root.hijos) {
            Arbol a = new Arbol();
            a.setRoot(hijo);
            a.imprimirPostorden(" "+ s);
        }
        if (root.getValor() == null) {
            System.out.println(s + root.getNombre());
        } else {
            System.out.println(s + root.getNombre() + ": " + root.getValor());}
    }
    public void busquedaEtiqueta(String s){
        if (root == null) return;
        if (root.getNombre().equals(s)){
        if (root.getValor() == null) {
            System.out.println(root.getNombre());
            for (NodoXML hijo : root.hijos) {
                Arbol b = new Arbol();
                b.setRoot(hijo);
                b.imprimirPreorden(" ");}
        } else {
            System.out.println(root.getNombre() + ": " + root.getValor());}}
        for (NodoXML hijo : root.hijos) {
            Arbol a = new Arbol();
            a.setRoot(hijo);
            a.busquedaEtiqueta(s);
        }
    }
    public void busquedaEtiquetaHeap(String s, Boolean maxHeap){
        LinkedList<NodoXML> LL = new LinkedList<>();
        busquedaEtiquetaHeapA(s, LL);
        if (LL.isEmpty()) {
            System.out.println("No se encontraron nodos con esa etiqueta");
            return;
        }
        HeapXML h = new HeapXML(maxHeap);
        LinkedList<NodoXML> ordenados = h.ordenar(LL, maxHeap);
        for (NodoXML n : ordenados) {
            if (n.getValor() != null) {
                System.out.println(s + ": " + n.getValor());
            }
        }
    }
    private void busquedaEtiquetaHeapA(String s, LinkedList<NodoXML> LL) {
        if (root == null) return;
        if (root.getNombre().equals(s) && root.getValor()!=null) {
            LL.add(root);
        }
        for (NodoXML hijo : root.hijos) {
            Arbol a = new Arbol();
            a.setRoot(hijo);
            a.busquedaEtiquetaHeapA(s, LL);
        }
    }
    public Boolean agregarNodo(String etiquetaPadre, NodoXML nuevoNodo){
        return agregarNodoA(etiquetaPadre, nuevoNodo);
    }
    private Boolean agregarNodoA(String etiquetaPadre, NodoXML nuevoNodo){
        if (root == null) return false;
        if (root.getNombre().equals(etiquetaPadre)){
            root.hijos.add(nuevoNodo);
            return true;
        }
        for (NodoXML hijo : root.hijos) {
            Arbol a = new Arbol();
            a.setRoot(hijo);
            if (a.agregarNodoA(etiquetaPadre, nuevoNodo)) return true;
        }
        return false;
    }
}