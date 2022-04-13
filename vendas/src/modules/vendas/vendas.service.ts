import httpStatus, { BAD_REQUEST } from "http-status";
import { envioMensagemAtualizastoqueProduto } from "../listener/produtos/produtoEstoque";
import produtoClient from "../produtos/client/produto.client";
import VendasException from "./exception/vendas.exception";
import { MensagemVenda } from "./model/mensagemVenda";
import { VendasModel } from "./model/vendas.model";
import vendasRepository from "./vendas.repository";


class VendaService {

  async criarVendas(venda: VendasModel, token: string){
    try{
      this.validaProduto(venda);
      await this.validaEstoque(venda, token);
      const response =  await vendasRepository.salvar(venda);
      if(response){
        const json = {vendasId: response.id, produtos: response.produtos}
        envioMensagemAtualizastoqueProduto(json)
      }
      return {status: httpStatus.SUCCESS, response}
    }catch(error: any){
      return {
        status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: error.message
      }
    }
  }


  async atualizaVenda(mensagem: MensagemVenda){
    try{
      if(mensagem.statusVenda && mensagem.vendaId){
        const vendas = await vendasRepository.buscarPorId(mensagem.vendaId);
        if(mensagem.statusVenda && mensagem.statusVenda !== vendas.status){
          vendas.status = mensagem.statusVenda;
          await vendasRepository.salvar(vendas);
        }
      }else{
        console.warn("Vendas inconsistente");
      }

    }catch(error: any){
      console.error("erro ao atualizar venda ");
      console.error(error.message);
    }
  }

  validaProduto(vendas: VendasModel): void{
    if(vendas.produtos?.length == 0) {
      throw new VendasException(BAD_REQUEST, "Pedido não informado")
    }
  }

  async validaEstoque(vendas: VendasModel, token: any){
    const estoque = await produtoClient.verificaEstoque(vendas.produtos, token);
    if(estoque){
      throw new VendasException(BAD_REQUEST, 'não há estoque para o produto');
    }
  }

}

export default new VendaService();
