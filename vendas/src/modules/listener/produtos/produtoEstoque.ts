import amqp from 'amqplib/callback_api';
import { RABBIT_MQ_URL } from '../../constantes/secrets';
import {
  PRODUTO_TOPIC,
  PRODUTO_ESTOQUE_QUEUE,
  PRODUTO_ESTOQUE_KEY
} from "../../rabbitmq/queue";


export function envioMensagemAtualizastoqueProduto(message: any){
  amqp.connect(RABBIT_MQ_URL, (error, connection) => {
    if(error){
      throw error;
    }
    console.info("Listener de produto iniciado");
    connection.createChannel((error: any, channel: any ) => {
      if(error){
        throw error
      }
        const jsonMessage = JSON.stringify(message);
        console.info(`mensagem enviada estoque produto ${jsonMessage}` );
        channel.publish(
          PRODUTO_TOPIC,
          PRODUTO_ESTOQUE_KEY,
          Buffer.from(jsonMessage)
          );
    });
  })
}
