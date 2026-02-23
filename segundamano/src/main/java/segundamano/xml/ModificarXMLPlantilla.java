package segundamano.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class ModificarXMLPlantilla {

    public static void main(String[] args) {
        try {
            // 1. Configuración de ficheros
            String entradaXML = "src/main/java/ejemplo/xml/tarea.xml";
            String salidaXML = "src/main/java/ejemplo/xml/tarea-2.xml"; // Donde guardar el modificado

            // 2. Cargar DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(entradaXML);

            // 3. Preparar XPath (para encontrar qué modificar)
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            // --- ZONA DE LÓGICA (PERSONALIZAR AQUÍ) ---
            
            // A) Buscamos las entradas con summary
            String consulta = "//tarea[estado='PENDIENTE']"; 
            NodeList entradas = (NodeList) xpath.evaluate(consulta, doc, XPathConstants.NODESET);

            for (int i = 0; i < entradas.getLength(); i++) {
                Element entry = (Element) entradas.item(i);
                
                // 1. Obtener el summary (sabemos que existe por el XPath)
                Element descripcion = (Element) entry.getElementsByTagName("descripcion").item(0);
                
                descripcion.setTextContent("URGENTE");

                Element nuevoElemento = doc.createElement("urgente"); // <urgente></urgente>
                Text textoUrgente = doc.createTextNode("¡Hacer YA!");
                nuevoElemento.appendChild(textoUrgente); // <urgente prioridad="alta">¡Hacer YA!</urgente>
                Element tarea = (Element) doc.getElementsByTagName("tarea").item(0);
                tarea.appendChild(nuevoElemento);
            }
            
            // --- FIN ZONA DE LÓGICA ---

            // 4. Guardar los cambios en un fichero nuevo
            guardarDocumento(doc, salidaXML);
            System.out.println("Documento modificado guardado en: " + salidaXML);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para guardar (no necesitas tocar esto)
    private static void guardarDocumento(Document doc, String ruta) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(ruta)));
    }
}
