import { api } from "./Api.service";

async function postLogin (cpf, password) {
  api.defaults.headers.common['Authorization'] = btoa(`${cpf}:${password}`)
  return api.post('/garcons/auth', {})
}

async function postRegister (data) {
  return api.post('/garcons', data)
}

const Waiter = { postLogin, postRegister }

export default Waiter
