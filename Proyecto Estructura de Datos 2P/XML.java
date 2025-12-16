import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class XML {
    public String xmlToString(String rutaArchivo) {
        try {
            String s = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            return s.trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    public Boolean comprobarXML (String Archivo){
        if (Archivo.charAt(0) == '<' && Archivo.charAt(Archivo.length()-1)=='>') {
        return comprobarXMLA(Archivo) && comprobarXMLB(Archivo);}
        else {return false;}
    }

    public Boolean comprobarXMLA(String Archivo){
        Stack<Character> stack = new Stack<>();
        for (char c : Archivo.toCharArray()) {
            if (c == '<') {
                stack.push(c);
            } else if (c == '>') {
                if (stack.isEmpty()) {
                    return false;}
                stack.pop();}}
        return stack.isEmpty();
    }
    public Boolean comprobarXMLB(String Archivo){
        Stack<String> stack = new Stack<>();
        int i = 0;
        while (i < Archivo.length()) {
            if (Archivo.charAt(i) == '<') {
                int fin = Archivo.indexOf('>', i);
                if (fin == -1) return false;
                String etiqueta = Archivo.substring(i + 1, fin).trim();
                if (etiqueta.startsWith("?") || etiqueta.startsWith("!")) {
                    i = fin + 1;
                    continue;
                }
                if (etiqueta.startsWith("/")) {
                    String nombre = etiqueta.substring(1).split(" ")[0];
                    if (stack.isEmpty() || !stack.peek().equals(nombre)) {
                        return false;
                    }
                    stack.pop();
                } else {
                    String nombre = etiqueta.split(" ")[0].split(">")[0];
                    if (!etiqueta.endsWith("/")) {
                        stack.push(nombre);
                    }
                }
                i = fin + 1;
            } else {
                i++;
            }
        }
        return stack.isEmpty();
    }

    public Arbol crearArbol(String xml){
        if (comprobarXML(xml)){
        Arbol a = new Arbol();
        Stack<NodoXML> pila = new Stack<>();
        for (String linea : xml.split("\n")) {
            linea = linea.trim();
            if (linea.isEmpty() || linea.startsWith("<?") || linea.startsWith("<!")) continue;
            if (linea.startsWith("</")) {
                if (!pila.isEmpty()) pila.pop();
            } else if (linea.startsWith("<") && !linea.contains("</")) {
                String etiq = linea.substring(1, linea.indexOf(">"));
                String nombre = etiq.split(" ")[0];
                NodoXML nodo = new NodoXML(nombre);
                if (etiq.contains(" ")) {
                    String[] partes = etiq.substring(nombre.length()).trim().split(" ");
                    for (String p : partes) {
                        if (p.contains("=")) {
                            String[] kv = p.split("=");
                            String k = kv[0];
                            String v = kv[1].replace("\"", "").replace("'", "");
                            nodo.atributos.put(k, v);
                        }
                    }
                }
                if (a.isEmpty()) {
                    a.setRoot(nodo);
                    pila.push(nodo);
                } else {
                    pila.peek().hijos.add(nodo);
                    pila.push(nodo);
                }
            } else if (linea.startsWith("<") && linea.contains("</")) {
                String etiq = linea.substring(1, linea.indexOf(">"));
                String nombre = etiq.split(" ")[0];
                String valor = linea.substring(linea.indexOf(">") + 1, linea.lastIndexOf("<"));
                NodoXML nodo = new NodoXML(nombre, valor);
                if (etiq.contains(" ")) {
                    String[] partes = etiq.substring(nombre.length()).trim().split(" ");
                    for (String p : partes) {
                        if (p.contains("=")) {
                            String[] kv = p.split("=");
                            String k = kv[0];
                            String v = kv[1].replace("\"", "").replace("'", "");
                            nodo.atributos.put(k, v);
                        }
                    }
                }
                if (!pila.isEmpty()) {
                    pila.peek().hijos.add(nodo);
                }
            }
        }
    return a;} else return null;
    }
    public String arbolToXML(Arbol a){
        if (a.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        arbolToXMLA(a.getRoot(), sb, 0);
        return sb.toString();
    }
    private void arbolToXMLA(NodoXML nodo, StringBuilder sb, int nivel){
        String indent = "";
        for (int i = 0; i < nivel; i++) indent += "    ";
        sb.append(indent).append("<").append(nodo.getNombre());
        if (!nodo.atributos.isEmpty()){
            for (String k : nodo.atributos.keySet()){
                sb.append(" ").append(k).append("=\"").append(nodo.atributos.get(k)).append("\"");
            }
        }
        if (nodo.hijos.isEmpty() && nodo.getValor() == null){
            sb.append("/>\n");
        } else if (nodo.hijos.isEmpty() && nodo.getValor() != null){
            sb.append(">").append(nodo.getValor()).append("</").append(nodo.getNombre()).append(">\n");
        } else {
            sb.append(">\n");
            if (nodo.getValor() != null){
                sb.append(indent).append("    ").append(nodo.getValor()).append("\n");
            }
            for (NodoXML hijo : nodo.hijos){
                arbolToXMLA(hijo, sb, nivel + 1);
            }
            sb.append(indent).append("</").append(nodo.getNombre()).append(">\n");
        }
    }
    public Boolean guardarXML(String contenido, String rutaArchivo){
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(rutaArchivo), contenido.getBytes());
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }
}