package compraventas.infraestructura.adaptadores;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import compraventas.aplicacion.puertos.salida.IPublicadorEventos;
import compraventas.eventos.Evento;
import compraventas.eventos.config.RabbitMQConfig;

// Adaptador del puerto de salida
@Component
public class PublicadorEventosRabbitMQ implements IPublicadorEventos {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publicarEvento(Evento evento) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.ROUTING_KEY + evento.getTipo(),
            evento);
    }
}