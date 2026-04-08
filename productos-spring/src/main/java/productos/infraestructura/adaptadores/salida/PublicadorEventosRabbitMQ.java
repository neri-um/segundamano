package productos.infraestructura.adaptadores.salida;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import productos.aplicacion.puertos.salida.ProductosPuerto;
import productos.eventos.Evento;
import productos.eventos.config.RabbitMQConfig;

@Component
public class PublicadorEventosRabbitMQ implements ProductosPuerto {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publicarEvento(Evento evento) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.ROUTING_KEY + evento.getTipo(),
            evento
        );
    }

}