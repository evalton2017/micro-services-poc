import { Request, Response } from 'express';
import {Pedido} from '../model/Pedido';

class HomeController {

  
  async home(req: Request, res: Response) {
    Pedido.find().then((respose)=>{
      console.log('response')
      console.log(respose)
    })
    const retorno = "Bem vindo a api Vendas"
    return res.status(200).json(retorno);

  }

}

export default HomeController;