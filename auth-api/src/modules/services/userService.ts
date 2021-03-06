import bcrypt from "bcrypt";
import { getRepository } from "typeorm";
import UserException from "../exception/UserException";
import { User } from "../model/user.model";
import httpStatus from 'http-status';
import jwt from 'jsonwebtoken';
import * as secrets from "../constantes/secrets";


class UserService {

    async buscarTodos(): Promise<User[]>{
        const userRepository = getRepository(User);
        const users = await userRepository.find({ relations: ['perfis'] });
        return users;
    }

    async cadastrarUser(user: User): Promise<any>{
        try{
            const userRepository = getRepository(User);
            const senhaTemp : any = user.password?.toString();
            const senha = await bcrypt.hash(senhaTemp, 10);
            user.password = senha;
            const usuario = userRepository.save(user);

            return { status: httpStatus.OK, usuario }
        }catch(err: any){
            return {
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR ,
                message: err.message
            };
        }

    }

    async buscarUserByEmail(email: string, authUser: User): Promise<any>{
        try{
            const userRepository = getRepository(User);
            const user = await userRepository.findOne({email: email}) as User;
            this.validateAuthenticatedUser(user, authUser);

            return {status: httpStatus.OK,  user: this.getUserTela(user)};
        }catch(err: any){
            return {
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR ,
                message: err.message
            };
        }

    }


    getUserTela(user: User | undefined): User{
        const usuario : User = new User();
        usuario.nome = user?.nome;
        usuario.cpf = user?.cpf;
        usuario.email = user?.email;
        usuario.perfis = user?.perfis;

        return usuario;
    }


    async getAccessToken(email: string, password: string): Promise<any> {
        try{
            this.validateAccesToken(email, password);
            const userRepo = getRepository(User);
            const user = await userRepo.findOne({email: email});
            const senha = user!.password as string;
            const validahash = await this.validatePassword(password, senha);
            let accesToken;
            if(validahash){
                accesToken = jwt.sign({user}, secrets.API_SECRET, {expiresIn: "1d"});
            }


            return { status: httpStatus.OK,  accesToken }
        }catch(err: any){
            return {
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR ,
                message: err.message
            };
        }

    }


    validateAccesToken(email: string, password: string){
        if(!email || !password){
          throw new UserException(httpStatus.UNAUTHORIZED, "Informe o email e o password");
        }
    }

    async validatePassword(password: string, hashPassword: string): Promise<boolean>{
      console.log(bcrypt.compareSync(password.toString(), hashPassword.toString()));
       if(await !bcrypt.compareSync(password.toString(), hashPassword.toString())){
            throw new UserException(httpStatus.UNAUTHORIZED, "Usuario ou senha n??o conferem.");
       }
       return true;
    }

    validateAuthenticatedUser(user: User, authUser: User){
        if(!authUser || user.id !== authUser.id){
            throw new UserException(httpStatus.FORBIDDEN, "Sem permiss??o")
        }
    }



}

export default UserService;

