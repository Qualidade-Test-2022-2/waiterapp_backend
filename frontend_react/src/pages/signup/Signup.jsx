import { useNavigate } from "react-router-dom"
import { useAuthentication } from "../../context/AuthContext"
import SignupForm from "./components/SignupForm"
import "./signup.css"

export default function Signup() {
  const { handleSignup } = useAuthentication()
  const navigate = useNavigate();

  const handleSubmit = (userData) => (event) => {
    event.preventDefault()

    handleSignup(userData)
      .then(() => navigate('/login'))
  }

  return (
    <div className="main_layout signup">
      <div className="signup__container">
        <button className="link" onClick={() => navigate(-1)} >{"<"} Voltar</button>
        <h2>Cadastre-se!</h2>
        <SignupForm handleSubmit={handleSubmit}/>
      </div>
    </div>
  )

}
