public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Main <archivo.xml>");
            return;
        }
        XML lector = new XML();
        String contenido = lector.xmlToString(args[0]);
        if (contenido.startsWith("Error:")) {
            System.out.println(contenido);
            return;
        }
        System.out.println("Validacion: " + lector.comprobarXML(contenido));
        Arbol a = lector.crearArbol(contenido);
        if (a == null) {
            System.out.println("Error: XML no válido");
            return;
        }
        System.out.println("\nPreorden:");
        a.imprimirPreorden("");
        System.out.println("\nPostorden:");
        a.imprimirPostorden("");
        System.out.println("\nBúsqueda name:");
        a.busquedaEtiqueta("name");
        System.out.println("\nOrdenamiento calories:");
        a.busquedaEtiquetaHeap("calories", false);
        System.out.println("\nAgregando nuevo nodo...");
        NodoXML nuevoFruit = new NodoXML("fruits");
        NodoXML nombre = new NodoXML("name", "Grape");
        NodoXML color = new NodoXML("color", "#800080");
        nuevoFruit.hijos.add(nombre);
        nuevoFruit.hijos.add(color);
        if (a.agregarNodo("root", nuevoFruit)){
            System.out.println("Nodo agregado exitosamente");
        }
        String xmlNuevo = lector.arbolToXML(a);
        if (lector.guardarXML(xmlNuevo, "fruits_modificado.xml")){
            System.out.println("Archivo guardado: fruits_modificado.xml");
        }
    }
}