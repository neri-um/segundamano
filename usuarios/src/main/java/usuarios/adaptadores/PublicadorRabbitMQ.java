package usuarios.adaptadores;

import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.time.Instant;
import usuarios.puertos.PublicadorEventos;
import utils.PropertiesReader;

public class PublicadorRabbitMQ implements PublicadorEventos {

	private static final String EXCHANGE = "bus";
	private Connection connection;
	private Channel channel;

	public PublicadorRabbitMQ() {
		try {
            String uri = resolverUri();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri(uri);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE, "topic", true);
		} catch (Exception e) {
			throw new RuntimeException("Error conectando publicador RabbitMQ", e);
		}
	}

    private static String resolverUri() throws Exception {
        String host = System.getenv("RABBITMQ_HOST");
        if (host != null && !host.isBlank()) {
            return "amqp://guest:guest@" + host + ":5672";
        }
        PropertiesReader props = new PropertiesReader("servicios.properties");
        return props.getProperty("rabbitmq.uri");
    }
	
	
	public void usuarioCreado(String idUsuario, String nombre, String apellidos, String email) {
		JsonObject obj = new JsonObject();
		obj.addProperty("tipo", "usuario-creado");
		obj.addProperty("timestamp", Instant.now().toString());
		obj.addProperty("idUsuario", idUsuario);
		obj.addProperty("nombre", nombre);
		obj.addProperty("apellidos", apellidos);
		obj.addProperty("email", email);
		publicar("bus.usuarios.usuario-creado", obj.toString());
	}

	public void usuarioModificado(String idUsuario, String nombre, String apellidos, String email) {
		JsonObject obj = new JsonObject();
		obj.addProperty("tipo", "usuario-modificado");
		obj.addProperty("timestamp", Instant.now().toString());
		obj.addProperty("idUsuario", idUsuario);
		obj.addProperty("nombre", nombre);
		obj.addProperty("apellidos", apellidos);
		obj.addProperty("email", email);
		publicar("bus.usuarios.usuario-modificado", obj.toString());
	}

	private void publicar(String routingKey, String mensaje) {
		try {
			channel.basicPublish(EXCHANGE, routingKey, null, mensaje.getBytes());
			System.out.println("[usuarios] Evento publicado: " + routingKey);
		} catch (Exception e) {
			System.err.println("[usuarios] Error publicando evento: " + e.getMessage());
		}
	}
}