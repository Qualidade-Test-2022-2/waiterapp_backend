import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { Header } from './components';
import { useAuthentication } from './context/AuthContext';
import { Login, Signup } from './pages';

export default function Router() {
  const { authenticated } = useAuthentication();
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route exact path="/" element={authenticated ? <Navigate to="/home" /> : <Navigate to="/login" />} />
        <Route path="/login" element={authenticated ? <Navigate to="/home" /> : <Login />} />
        <Route path="/signup" element={authenticated ? <Navigate to="/home" /> : <Signup />} />
        <Route path="/home" element={!authenticated ? <Navigate to="/login" /> : <Signup />} />
      </Routes>
    </BrowserRouter>
  )
}

