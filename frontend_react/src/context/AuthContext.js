import { createContext, useContext, useState } from "react";
import Client from "../services/Client.service";

export const AuthContext = createContext();

export default function AuthProvider({ children }) {
  const [authenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState(null);

  const handleLogin = (cpf, password) => {
    Client.postLogin(cpf, password)
      .then((response) => {
        if (!response.data) return
        setUser(response.data);
        setAuthenticated(true);
      });
  }

  const handleSignup = (userData) => {
    return Client.postRegister(userData)
      .then((response) => {
        setUser(response.data);
        setAuthenticated(true);
      });
  }

  const handleLogout = () => {
    setAuthenticated(false);
    setUser(null);
  }

  const value = { authenticated, user, handleLogin, handleSignup, handleLogout }

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuthentication = () => {
  const context = useContext(AuthContext);
  return context;
}
