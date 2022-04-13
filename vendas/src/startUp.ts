import 'reflect-metadata';
import 'dotenv/config';
import express, {Request, Response, NextFunction} from 'express';
import 'express-async-errors';
import { json, urlencoded } from 'body-parser';
import { AppLogger } from './util/appLogger';
import compression from 'compression';
import * as cors from 'cors';
import AppError from './shared/errors/AppError';
import Database from './util/db';
import vendasRouter from './routes/vendas-router';
import checkToken from './modules/config/auth/checkToken';
import {connectRabbitMq} from "./modules/rabbitmq/rabbitmq";
import { envioMensagemAtualizastoqueProduto } from './modules/listener/produtos/produtoEstoque';


class StartUp {
  public app: express.Application;
  private _db: Database;

  constructor() {
    this.app = express();
    this._db = new Database();
    this._db.createConnection();
    connectRabbitMq();
    this.configureMiddleware();
    this.routes();
    this.app.use((error: Error, request: Request, response: Response, next: NextFunction)=>{
      if(error instanceof AppError){
        return response.status(error.statusCode).json({
          status: 'error',
          message: error.message
        })
      }
      return response.status(500).json({
        status: 'error',
        message: 'Internal server error'
      })
    });

  }

  routes() {
   // this.app.use(checkToken);
    this.app.use("/api", vendasRouter);
    this.app.get("/teste", (req, res)=>{
      try{
        envioMensagemAtualizastoqueProduto([
          {
            produtoId: 1001,
            quantidade: 2
          },
          {
            produtoId: 1002,
            quantidade: 3
          }

        ])
        return res.status(200).json("Sucessso");
      }catch(err){
        console.log(err)
        return res.status(500).json(err);
      }
    });
  }

  private configureMiddleware() {
    this.app.use(express.json());
    this.app.use(express.urlencoded({ extended: false }));
    this.app.use(json({ limit: '50mb' }));
    this.app.use(compression());
    this.app.use(urlencoded({ limit: '50mb', extended: true }));
    this.enableCors();
    AppLogger.configureLogger();
  }

  enableCors() {
    const options: cors.CorsOptions = {
      methods: "GET,OPTIONS,PUT,POST,DELETE",
      origin: "*",
    }
    this.app.use(cors.default(options));
  }

}

export default new StartUp();
