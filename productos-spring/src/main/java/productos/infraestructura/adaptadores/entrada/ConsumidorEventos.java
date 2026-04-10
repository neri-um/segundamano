package productos.infraestructura.adaptadores.entrada;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import productos.aplicacion.puertos.entrada.ManejadorEventos;
import productos.eventos.config.RabbitMQConfig;
@Component
public class ConsumidorEventos {
    @Autowired private ManejadorEventos manejadorEventos;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleEvent(Message mensaje) {
        String body = new String(mensaje.getBody());
        System.out.println("[productos] Mensaje recibido: " + body);

        String routingKey = mensaje.getMessageProperties().getReceivedRoutingKey();

        if (routingKey.equals("bus.compraventas.compraventa-creada")) {
            JsonObject obj = JsonParser.parseString(body).getAsJsonObject();
            manejadorEventos.compraventaCreada(
                obj.get("id").getAsString(),
                obj.get("idProducto").getAsString()
            );
        } else if (routingKey.equals("bus.usuarios.usuario-modificado")) {
            JsonObject obj = JsonParser.parseString(body).getAsJsonObject();
            manejadorEventos.usuarioModificado(
                obj.get("idUsuario").getAsString(),
                obj.get("nombre").getAsString(),
                obj.get("apellidos").getAsString(),
                obj.get("email").getAsString()
            );
        }
    }
}