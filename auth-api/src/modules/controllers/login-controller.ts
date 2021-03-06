import { Request, Response } from 'express';
import { User } from '../model/user.model';
import UserService from '../services/userService';

class LoginController {

  async cadastrar(req: Request, res: Response) {
    const service = new UserService();
    const user = req.body;
    const response = await service.cadastrarUser(user);
    return res.status(response.status).json(response);
  }

  async buscarUser(req: Request, res: Response) {
    const service = new UserService();
    const email = req.params.email;
    const userAuth = req.user as User;
    const user = await service.buscarUserByEmail(email, userAuth);
    return res.status(user.status).json(user);
  }

  async buscarTodos(req: Request, res: Response) {
    const service = new UserService();
    const users = service.buscarTodos();
    return res.status(200).json(users);
  }

  async getAccessToken(req: Request, res: Response) {
      const {email, password} = req.body;
      const service = new UserService();
      console.log([email, password])
      const accesToken = await service.getAccessToken(email, password);
      return res.status(accesToken.status).json(accesToken);

  }



}

export default LoginController;
