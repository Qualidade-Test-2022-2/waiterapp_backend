import { api } from "./Api.service";

async function postLogin (cpf, password) {
  return api.post('/garcons/cpf', { cpf }, {
    headers: {
      Authorization: btoa(`${cpf.replace(/[.-]/g, '')}:${password}`)
    }
  })
  .then(() => api.defaults.headers.common['Authorization'] = btoa(cpf))
}

async function postRegister (data) {
  return api.post('/garcons', data)
}

const Waiter = { postLogin, postRegister }

export default Waiter
