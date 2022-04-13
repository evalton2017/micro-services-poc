import axios from "axios";
import { PRODUTO_API } from "../../../modules/constantes/secrets";

class ProdutoClient {

  async verificaEstoque(produtos: any, token: any){
    try{
      const headersToken ={
        Authorization: `Bearer ${token}`
      }
      axios.post(`${PRODUTO_API}/verifica-estoque`,
      produtos,
      {headers: headersToken})
      .then((res)=>{
          return true;
      }).catch((error)=>{
        console.error(error.message);
        return false;
      });

    }catch(error){
        return false;
    }
  }
}

export default new ProdutoClient();
