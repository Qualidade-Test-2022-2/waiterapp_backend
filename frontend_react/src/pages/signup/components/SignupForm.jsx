import { useReducer } from 'react'

const reducer = (state, action) => {
  switch (action.type) {
    case 'UPDATE':
      return {
        ...state,
        ...action.payload
      }
    default:
      return state
  }
}

export default function SignupForm ({ handleSubmit }) {
  const [{ name, email, cpf, password }, dispatch] = useReducer(reducer, { name: '', email: '', cpf: '', password: '' })

  const handleChange = (key) => (event) => {
    dispatch({
      type: "UPDATE",
      payload: { [key]: event.target.value }
    })
  }

  const userData = {
    nome: name,
    email: email,
    cpf: cpf,
    password
  }

  return (
    <form className="signup__form" onSubmit={handleSubmit(userData, 'client')}>
      <fieldset>
        <label>Nome:</label>
        <input type="text" value={name} onChange={handleChange('name')}/>
      </fieldset>

      <fieldset>
        <label>Email:</label>
        <input type="text" value={email} onChange={handleChange('email')}/>
      </fieldset>

      <fieldset>
        <label>Cpf:</label>
        <input type="text" value={cpf} onChange={handleChange('cpf')}/>
      </fieldset>

      <fieldset>
        <label>Senha:</label>
        <input type="password" value={password} onChange={handleChange('password')}/>
      </fieldset>

      <input type="submit" value="Cadastrar"/>
    </form>
  )
}
