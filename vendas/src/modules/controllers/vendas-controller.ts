import { Request, Response } from 'express';
import {Vendas} from '../vendas/model/Vendas';
import { VendasModel } from '../vendas/model/vendas.model';
import vendasService from '../vendas/vendas.service';

class VendasController {


  async buscarVendas(req: Request, res: Response) {
    //Vendas.collection.drop();
    const teste = await Vendas.find()
    return res.status(200).json(teste);

  }

  async criarVendas(req: Request, res: Response) {
    const {authorization} = req.headers;
    const vendas: VendasModel = req.body as VendasModel;
    try{
       vendasService.criarVendas(vendas, authorization as string);
    }catch(erro){
      console.log(erro)
    }
    return res.status(200).json("venda criada");

  }



}

export default VendasController;
