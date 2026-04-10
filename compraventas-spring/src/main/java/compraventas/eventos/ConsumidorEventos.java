package compraventas.eventos;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import compraventas.aplicacion.puertos.entrada.IServicioCompraventas;
import compraventas.eventos.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorEventos {

    @Autowired
    private IServicioCompraventas servicio;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleEvent(Message mensaje) {
        String body = new String(mensaje.getBody());
        String routingKey = mensaje.getMessageProperties().getReceivedRoutingKey();

        if (routingKey.equals("bus.usuarios.usuario-modificado")) {
            JsonObject obj = JsonParser.parseString(body).getAsJsonObject();
            servicio.actualizarNombreUsuario(
                obj.get("idUsuario").getAsString(),
                obj.get("nombre").getAsString(),
                obj.get("apellidos").getAsString()
            );
        }
    }
}