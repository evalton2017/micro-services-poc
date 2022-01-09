import "reflect-metadata";
import {createConnection} from "typeorm";
import { AppLogger } from "./appLogger";


//CONFIGURAÇÕES LOCAL
const DB_LOCAL = 'vendas';
const USERNAME_LOCAL= 'admin';
const PASSWORD_LOCAL='admin234';
const HOST_LOCAL='localhost';

class Database{

  createConnection(){
    createConnection({
      type: "postgres",
      host: HOST_LOCAL,
      port: 5432,
      username: USERNAME_LOCAL,
      password: PASSWORD_LOCAL,
      database: DB_LOCAL,
      entities: [
        `${__dirname}/../**/*.model.{ts,js}`
      ],
      synchronize: true,
      logging: false
    }).then(connection => {
      AppLogger.info("Criando/atualizando tabelas no banco")
    }).catch((error) => {
      AppLogger.error(error);
    });

  }

}

export default Database;
