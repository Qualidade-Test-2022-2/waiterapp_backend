import LoginForm from "./components/LoginForm";
import "./login.css";

export default function Login() {
  return (
    <div className="main_layout login">
      <div className="login__container">
        <h2>Faça login</h2>
        <LoginForm />
      </div>
    </div>
  );
}
