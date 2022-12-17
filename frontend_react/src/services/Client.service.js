import { api } from "./Api.service";

async function postLogin (cpf, password) {
  api.defaults.headers.common['Authorization'] = btoa(`${cpf}:${password}`)
  return api.post('/clientes/auth', {})
}

async function postRegister (data) {
  return api.post('/clientes', data)
}

const Client = { postLogin, postRegister }

export default Client
