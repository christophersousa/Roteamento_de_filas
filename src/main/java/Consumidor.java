import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.List;

public class Consumidor {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor");

        String NOME_FILA = "timestamp";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");
        Connection conexao = connectionFactory.newConnection();

        Channel channel = conexao.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        channel.queueBind(NOME_FILA, EXCHANGE_NAME, NOME_FILA);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(NOME_FILA, true, deliverCallback, consumerTag -> {
        });
    }

}
