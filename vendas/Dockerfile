FROM node:14
WORKDIR .
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 4040
CMD ["npm", "start"]
