import amqp from 'amqplib/callback_api';
import {
  PRODUTO_ESTOQUE_KEY,
  PRODUTO_TOPIC,
  PRODUTO_ESTOQUE_QUEUE,
  VENDAS_CONFIRMADA_KEY,
  VENDAS_CONFIRMADA_QUEUE
} from "./queue";
import { RABBIT_MQ_URL } from '../constantes/secrets';
import {listenVendaConfirmacao} from '../listener/vendas/vendasConfirmacao';


const TWO_SECONDS = 2000;
const HALF_MINUTE = 30000;
const CONTAINER_ENV = "container";

export async function connectRabbitMq(){
  const env = process.env.NODE_ENV;

  if(CONTAINER_ENV === env){
    console.info("iniciando RabbitMQ....");
    setInterval(()=>{
      createConnectRabbbitQueues();
    }, HALF_MINUTE)
  }else{
    console.info("iniciando RabbitMQ Local....");
    await createConnectRabbbitQueues();
  }


  function createQueue(connection: any, queue: string, routingKey: string, topic: string){
    connection.createChannel((error: any, channel: any ) => {
      if(error){
        throw error
      }
        channel.assertExchange(topic, "topic", {durable: true});
        channel.assertQueue(queue, {durable: true});
        channel.bindQueue(queue, topic, routingKey);
    })
  }

  async function createConnectRabbbitQueues(){
    amqp.connect(RABBIT_MQ_URL, (err, connection)=>{
      if(err){
        throw err;
      }
      console.info("RabbitMQ iniciado com sucesso....");
      createQueue(connection,PRODUTO_ESTOQUE_QUEUE,PRODUTO_ESTOQUE_KEY, PRODUTO_TOPIC);
      createQueue(connection,VENDAS_CONFIRMADA_QUEUE,VENDAS_CONFIRMADA_KEY, PRODUTO_TOPIC);
      setTimeout(() => {
        listenVendaConfirmacao();
        connection.close();
      }, TWO_SECONDS);
    });

  }

}
