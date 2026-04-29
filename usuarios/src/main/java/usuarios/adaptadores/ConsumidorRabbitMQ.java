package usuarios.adaptadores;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import servicio.FactoriaServicios;
import usuarios.puertos.ManejadorEventos;
import utils.PropertiesReader;

@WebListener
public class ConsumidorRabbitMQ implements ServletContextListener {

	 private ManejadorEventos manejadorEventos;

	    private Connection connection;
	    private Channel channel;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
	        this.manejadorEventos = FactoriaServicios.getServicio(ManejadorEventos.class);
            String uri = resolverUri();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri(uri);
			connection = factory.newConnection();
			channel = connection.createChannel();

			String exchangeName = "bus";
			channel.exchangeDeclare(exchangeName, "topic", true);

			final String queueName = "usuarios";
			final String bindingKey = "bus.compraventas.#";
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, exchangeName, bindingKey);

			channel.basicConsume(queueName, false, "usuarios-consumer", new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String tag, Envelope envelope, AMQP.BasicProperties props, byte[] body)
						throws IOException {

					long deliveryTag = envelope.getDeliveryTag();
					String routingKey = envelope.getRoutingKey();
					String contenido = new String(body);
					JsonObject obj = JsonParser.parseString(contenido).getAsJsonObject();

					if (routingKey.equals("bus.compraventas.compraventa-creada")) {
						manejadorEventos.compraventaCreada(obj.get("id").getAsString(),
								obj.get("idVendedor").getAsString(), obj.get("idComprador").getAsString(),
								obj.get("idProducto").getAsString());
					}
					channel.basicAck(deliveryTag, false);
				}
			});
			System.out.println("consumidor usuarios esperando ...");
		} catch (Exception e) {
			throw new RuntimeException(e);
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
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			if (channel != null)
				channel.close();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}