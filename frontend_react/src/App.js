import './App.css';
import AuthProvider from './context/AuthContext';
import Routes from './Router';

function App() {
  return (
    <AuthProvider>
      <Routes />
    </AuthProvider>
  );
}

export default App;
