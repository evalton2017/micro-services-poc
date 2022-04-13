import amqp from 'amqplib/callback_api';
import vendasService from '../../vendas/vendas.service';
import { RABBIT_MQ_URL } from '../../constantes/secrets';
import {
  VENDAS_CONFIRMADA_QUEUE
} from "../../rabbitmq/queue";
import { MensagemVenda } from 'src/modules/vendas/model/mensagemVenda';

export function listenVendaConfirmacao(){
  amqp.connect(RABBIT_MQ_URL, (error, connection) => {
    if(error){
      throw error;
    }
    console.info("Listener de vendas iniciado");
    connection.createChannel((error: any, channel: any ) => {
      if(error){
        throw error
      }
        channel.consume(
          VENDAS_CONFIRMADA_QUEUE,
          (mensagem: any) => {
            console.info(`Mensgem recebida ${mensagem.content.toString()}`);
            const json= JSON.parse(mensagem.content);
            vendasService.atualizaVenda(json);
          },
          {
            noAck: true,
          }
          );
    })
  })
}
