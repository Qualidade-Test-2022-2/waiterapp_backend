import { api } from "./Api.service";

async function postLogin (cpf, password) {
  return api.post('/clientes/auth', {}, {
    headers: {
      "Authorization": btoa(`${cpf.replace(/[.-]/g, '')}:${password}`)
    }
  })
  .then(() => api.defaults.headers.common['Authorization'] = btoa(cpf))
}

async function postRegister (data) {
  return api.post('/clientes', data)
}

const Client = { postLogin, postRegister }

export default Client
