import { useState } from "react"
import { Link } from "react-router-dom"
import { useAuthentication } from "../../../context/AuthContext"
import TypeSelector from "./TypeSelector"

export default function LoginForm() {
  const [cpf, setCpf] = useState('')
  const [password, setPassword] = useState('')
  const [type, setType] = useState('client')
  const { handleLogin } = useAuthentication()

  const handleSubmit = (event) => {
    event.preventDefault();
    handleLogin(cpf, password, type);
  }

  return (
    <form onSubmit={handleSubmit}>
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
