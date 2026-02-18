package segundamano.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class CreadorXML {

    public static void main(String[] args) {
        String rutaSalida = "salida.xml"; // Nombre del archivo a crear

        try {
            // 1. Crear el Documento vacío
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // ----------------------------------------------------
            // ZONA DE EDICIÓN: Aquí construyes tu XML
            // ----------------------------------------------------

            // Ejemplo: <tarea id="T-001">
            Element raiz = doc.createElement("tarea");
            raiz.setAttribute("id", "T-001");
            doc.appendChild(raiz);

            // Método helper para añadir hijos con texto rápido
            // Crea: <nombre>Comprar Pan</nombre>
            agregarHijoConTexto(doc, raiz, "nombre", "Comprar Pan");

            // Crea: <descripcion>Ir al súper</descripcion>
            agregarHijoConTexto(doc, raiz, "descripcion", "Ir al súper");

            // Ejemplo estructura anidada: <estados><estado>...</estado></estados>
            Element estados = doc.createElement("estados");
            raiz.appendChild(estados);

            Element estado = doc.createElement("estado");
            estados.appendChild(estado);
            
            agregarHijoConTexto(doc, estado, "nombre", "PENDIENTE");
            agregarHijoConTexto(doc, estado, "fechaInicio", "2026-01-12");

            // ----------------------------------------------------
            // FIN ZONA DE EDICIÓN
            // ----------------------------------------------------

            // 2. Guardar en fichero (Formateado bonito)
            guardarXML(doc, rutaSalida);
            
            System.out.println("XML creado correctamente en: " + rutaSalida);

        } catch (Exception e) {
            System.err.println("Error creando el XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE UTILIDAD (NO TOCAR) ---

    /**
     * Crea un elemento con texto dentro y lo añade al padre.
     * Ejemplo: agregarHijoConTexto(doc, padre, "nombre", "Pepe") -> <nombre>Pepe</nombre>
     */
    private static void agregarHijoConTexto(Document doc, Element padre, String etiqueta, String contenido) {
        Element elem = doc.createElement(etiqueta);
        Text texto = doc.createTextNode(contenido);
        elem.appendChild(texto);
        padre.appendChild(elem);
    }

    private static void guardarXML(Document doc, String ruta) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        
        // Configuración para que salga indentado 
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        File archivo = new File(ruta);
        transformer.transform(new DOMSource(doc), new StreamResult(archivo));
    }
}
