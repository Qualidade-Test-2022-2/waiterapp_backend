import { useAuthentication } from "../../context/AuthContext";
import LoginForm from "./components/LoginForm";
import "./login.css";

export default function Login() {
  const { handleLogin } = useAuthentication()

  const handleSubmit = (cpf, password) => (event) => {
    event.preventDefault();
    handleLogin(cpf, password);
  }

  return (
    <div className="main_layout login">
      <div className="login__container">
        <h2>Faça login</h2>
        <LoginForm handleSubmit={handleSubmit} />
      </div>
    </div>
  );
}
