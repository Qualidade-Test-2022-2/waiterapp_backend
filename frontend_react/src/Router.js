import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { Header } from './components';
import { useAuthentication } from './context/AuthContext';
import { Login, Menu, Signup } from './pages';

export default function Router() {
  const { authenticated, user } = useAuthentication();

  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route exact path="/" element={authenticated ? <Navigate to="/menu" /> : <Navigate to="/login" />} />
        <Route path="/login" element={authenticated ? <Navigate to="/menu" /> : <Login />} />
        <Route exact path="/signup" element={authenticated ? <Navigate to="/menu" /> : <Signup type='client'/>} />
        <Route path="/signup/waiter" element={authenticated && user.type !== "waiter" ? <Navigate to="/login" /> : <Signup type='waiter'/>}/>
        <Route path="/menu" element={!authenticated ? <Navigate to="/login" /> :<Menu />}/>
      </Routes>
    </BrowserRouter>
  )
}

