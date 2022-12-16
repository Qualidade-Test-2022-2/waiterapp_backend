import { createContext, useContext, useState } from "react";
import { Waiter } from "../services";
import Client from "../services/Client.service";

export const AuthContext = createContext();

export default function AuthProvider({ children }) {
  const [authenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState(null);

  const handleLogin = (cpf, password, type) => {
    if (type === 'client') {
      Client.postLogin(cpf, password)
        .then((response) => {
          setUser({...response.data, type: 'client'});
          setAuthenticated(true);
        });
    } else {
      Waiter.postLogin(cpf, password)
        .then((response) => {
          setUser({...response.data, type: 'waiter'});
          setAuthenticated(true);
        });
    }
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
