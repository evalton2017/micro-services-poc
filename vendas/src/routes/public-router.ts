import * as express from "express";
import HomeController from "../modules/controllers/home-controller";


const publicRouter = express.Router();
const homeController = new HomeController();

publicRouter.route('/home').get(homeController.home);

export default publicRouter;
