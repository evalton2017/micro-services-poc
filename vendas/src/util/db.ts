import "reflect-metadata";
import mongoose from "mongoose";
import { MONGO_DB_URL } from "../modules/constantes/secrets";


//CONFIGURAÇÕES LOCAL
const DB_LOCAL = 'vendas';
const USERNAME_LOCAL= 'admin';
const PASSWORD_LOCAL='admin234';
const HOST_LOCAL='localhost';

class Database{

  async createConnection(): Promise<void> {
    await mongoose.connect(MONGO_DB_URL);
  }

}

export default Database;
