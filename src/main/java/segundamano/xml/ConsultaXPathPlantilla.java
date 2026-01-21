package segundamano.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConsultaXPathPlantilla {

	public static void main(String[] args) {
		try {
			// ==========================================
			// 1. ZONA DE CONFIGURACIÓN (CAMBIA ESTO)
			// ==========================================

			String rutaFicheroXML = "src/main/java/ejemplo/xml/tarea.xml";

			// TU CONSULTA XPATH AQUÍ:
			// Ejemplo: "/feed/entry[summary]" -> Entradas que tienen summary
			/**
			 * /tarea/nombre: Entras en tarea, bajas a nombre y coges eso.
			 * 
			 * /tarea[nombre]: Entras en tarea, compruebas ("filtras") si tiene hijo nombre,
			 * y si sí, te quedas con la TAREA.
			 */
			String expresionXPath = "/tarea/nombre";

			// ==========================================

			// 2. Cargar el documento XML en memoria (DOM)
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(rutaFicheroXML);

			// 3. Preparar XPath
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile(expresionXPath);

			// 4. Ejecutar consulta (Pedimos una lista de NODOS)
			NodeList listaNodos = (NodeList) expr.evaluate(documento, XPathConstants.NODESET);

			// 5. Mostrar resultados
			System.out.println("Resultados encontrados: " + listaNodos.getLength());
			System.out.println("--------------------------------------------------");

			for (int i = 0; i < listaNodos.getLength(); i++) {
				Node nodo = listaNodos.item(i);

				// Opción A: Mostrar solo el texto del nodo
				// System.out.println(nodo.getTextContent());

				// Opción B: Mostrar nombre de etiqueta y contenido (más útil para depurar)
				System.out.println("Elemento: <" + nodo.getNodeName() + ">");
				System.out.println("Contenido: " + nodo.getTextContent().trim()); // .trim() limpia espacios extra
				System.out.println("--------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
