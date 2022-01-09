import { Request, Response } from 'express';

class HomeController {

  
  async home(req: Request, res: Response) {
    const retorno = "Bem vindo a api Vendas"
    return res.status(200).json(retorno);

  }

}

export default HomeController;