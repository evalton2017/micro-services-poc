import * as express from "express";
import VendasController from "../modules/controllers/vendas-controller";


const vendasRouter = express.Router();
const vendasController = new VendasController();

vendasRouter.route('/vendas').get(vendasController.buscarVendas);
vendasRouter.route('/vendas/criar').post(vendasController.criarVendas);
export default vendasRouter;
