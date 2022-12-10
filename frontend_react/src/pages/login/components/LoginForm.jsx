import { useState } from "react"
import { Link } from "react-router-dom"
import TypeSelector from "./TypeSelector"

export default function LoginForm({ handleSubmit }) {
  const [cpf, setCpf] = useState('')
  const [password, setPassword] = useState('')
  const [type, setType] = useState('client')

  return (
    <form onSubmit={handleSubmit(cpf, password, type)}>
      <fieldset>
        <label>Desejo entrar como um:</label>
        <TypeSelector value={type} onChange={(value) => setType(value)} />
      </fieldset>

      <fieldset>
        <label>CPF:</label>
        <input type="text" value={cpf} onChange={(e) => setCpf(e.target.value)} />
      </fieldset>

      <fieldset>
        <label>Senha:</label>
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      </fieldset>

      <fieldset>
        <Link to="/signup">Não tem cadastro? Crie sua conta</Link>
      </fieldset>
      <input type="submit" value="Entrar" />
    </form>
  )
}
