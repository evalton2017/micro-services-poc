import { VendasModel } from "./model/vendas.model";
import {Vendas} from './model/Vendas';

class VendaRepository {

  async salvar(venda: VendasModel){
    try{
      return await Vendas.create(venda);
    }catch(error: any){
      console.error(error.message);
    }
  }

  async buscarPorId(idVenda: string){
    try{
      return await Vendas.findById(idVenda);
    }catch(error: any){
      console.error(error.message);
    }
  }

  async listar(){
    try{
      return await Vendas.find();
    }catch(error: any){
      console.error(error.message);
    }
  }

}

export default new VendaRepository();

