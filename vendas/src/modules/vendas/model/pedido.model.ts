import { UsuarioModel } from "src/modules/model/usuario.model";
import { ProdutoModel } from "./produto.model";

export class PedidoModel {
  produtos?: ProdutoModel;
  usuario?: UsuarioModel;
  status?: string;
  createdAt?: Date;
  updateAt?: Date;

}
