import mongoose from 'mongoose';

const PedidoSchema = new mongoose.Schema({
    produtos: {
        type: Array,
        required: true,
    },
    usuario: {
        type: Object,
        required: true
    },
    status: {
        type: String,
        required: true
    },
    createdAt: {
        type: Date,
        required: true
    },
    updateAt: {
        type: Date,
        required: true
    }
});

const Pedido = mongoose.model('Pedido', PedidoSchema)

export {Pedido}