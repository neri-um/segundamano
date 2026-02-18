package segundamano.xml;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXParseException;

public class ProgramaValidador {
public static void main(String[] args) throws Exception {
		
		String ficheroEsquema = "src/main/java/arte/xml/arte.xsd";
		String nombreFichero = "src/main/java/arte/xml/obra.xml";
		
		SchemaFactory factoriaSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		// Construye el esquema 		
		Schema esquema = factoriaSchema.newSchema(new File(ficheroEsquema));
		
		// Solicita al esquema la construcción de un validador 
		Validator validador = esquema.newValidator();
		
		// Registra el manejador de eventos de error 
		Validador miValidador = new Validador();
		validador.setErrorHandler(miValidador);
		
		// Solicita la validación del fichero XML 
		validador.validate(new StreamSource(nombreFichero));
		
		// Mostrar errrores
		
		for (SAXParseException error: miValidador.getErrores()) {
			System.out.println(error.getMessage());
			System.out.printf("\t linea (%d) - columna (%d)\n", error.getLineNumber(), error.getColumnNumber());
			
		}
		
		System.out.println("fin.");
	}

}