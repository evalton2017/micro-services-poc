import mongoose from "mongoose";

const Schema = mongoose.Schema;
const model = mongoose.model;

const VendasSchema = new Schema({
  produtos:{
    type: Array,
    required: true,
  },
  usuario: {
    type: Object,
    required: true,
  },
  status: {
    type: String,
    required: true,
  },
  createdAt:{
    type: Date,
    required: true
  },
  updateAt:{
    type: Date,
    required: true
  }

});

const Vendas = mongoose.model('Vendas', VendasSchema)

export {Vendas}
