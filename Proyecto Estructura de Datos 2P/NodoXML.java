import java.util.LinkedList;
import java.util.HashMap;

public class NodoXML {
    public String nombre;
    public String valor;
    public LinkedList<NodoXML> hijos;
    public HashMap<String, String> atributos;

    public NodoXML (String nombre){
        this.nombre = nombre;
        this.hijos = new LinkedList<>();
        this.atributos = new HashMap<>();
    }
    public NodoXML (String nombre, String valor){
        this.nombre = nombre;
        this.valor = valor;
        this.hijos = new LinkedList<>();
        this.atributos = new HashMap<>();
    }
    public LinkedList<NodoXML> getHijos(){return hijos;}
    public String getNombre(){return nombre;}
    public String getValor(){return valor;}
    public HashMap<String, String> getAtributos(){return atributos;}
}
