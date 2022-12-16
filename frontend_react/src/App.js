import './App.css';
import AuthProvider from './context/AuthContext';
import { CartProvider } from './context/CartContext';
import Routes from './Router';

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Routes />
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
