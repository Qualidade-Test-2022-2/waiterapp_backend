import { api } from "./Api.service";

async function postLogin (cpf) {
  return api.post('/clientes/cpf', { cpf })
}

async function postRegister (data) {
  return api.post('/clientes', data)
}

const Client = { postLogin, postRegister }

export default Client
