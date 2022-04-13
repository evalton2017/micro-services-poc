const env = process.env;

export const MONGO_DB_URL = env.MONGO_DB_URL ? env.MONGO_DB_URL : "mongodb://admin:admin234@localhost:27017/admin"
export const API_SECRET = env.API_SECRET ? env.API_SECRET : "ZHVrZWNvcmFqb3NvY29kaWdvZW1iYXNlNjRlbmNvbmRlcg==";
export const RABBIT_MQ_URL = env.RABBIT_MQ_URL ? env.RABBIT_MQ_URL : "amqp://localhost:5672"
export const PRODUTO_API = env.PRODUTO_API ? env.PRODUTO_API : "http://localhost:9091/api/produto"
