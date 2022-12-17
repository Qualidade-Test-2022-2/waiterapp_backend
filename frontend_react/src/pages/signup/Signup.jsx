import { useNavigate } from "react-router-dom"
import { useAuthentication } from "../../context/AuthContext"
import SignupForm from "./components/SignupForm"
import "./signup.css"

export default function Signup({ type }) {
  const { handleSignup } = useAuthentication()
  const navigate = useNavigate();

  const handleSubmit = (userData) => (event) => {
    event.preventDefault()
    handleSignup(userData, type)
      .then(() => navigate('/login'))
  }

  return (
    <div className="main_layout signup">
      <div className="signup__container">
        {type !== 'waiter' && <button className="link" onClick={() => navigate(-1)} >{"<"} Voltar</button>}
        <h2>{type === 'waiter' ? "Cadastre um novo garçom" : "Cadastre-se!"}</h2>
        <SignupForm handleSubmit={handleSubmit}/>
      </div>
    </div>
  )

}
