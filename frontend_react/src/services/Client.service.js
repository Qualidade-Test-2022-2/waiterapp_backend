import { api } from "./Api.service";

async function postLogin (cpf, password) {
  return api.post('/clientes/cpf', { cpf }, {
    headers: {
      Authorization: btoa(`${cpf}:${password}`)
    }
  })
  .then(() => api.defaults.headers.common['Authorization'] = btoa(cpf))
}

async function postRegister (data) {
  return api.post('/clientes', data)
}

const Client = { postLogin, postRegister }

export default Client
