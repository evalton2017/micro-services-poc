import { Pedido } from "./model/Pedido";
import { PedidoModel } from "./model/pedido.model";

class PedidoRepository {

  async salvar(pedido: PedidoModel){
    try{
      return await Pedido.create(pedido);
    }catch(error: any){
      console.error(error.message);
    }
  }

  async buscarPorId(idPedido: number){
    try{
      return await Pedido.findById(idPedido);
    }catch(error: any){
      console.error(error.message);
    }
  }

  async listar(){
    try{
      return await Pedido.find();
    }catch(error: any){
      console.error(error.message);
    }
  }

}

export default new PedidoRepository();
