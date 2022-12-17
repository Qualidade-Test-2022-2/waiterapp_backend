import { Link } from 'react-router-dom'
import { useAuthentication } from '../../context/AuthContext'
import logo from '../../images/logo-waiterapp.png'
import logout from '../../images/logout.svg'
import CartShow from './components/CartShow'
import './header.css'

export default function Header() {
  const { authenticated, user, handleLogout } = useAuthentication()

  return (
    <header className="header">
      <div className='header__info'>
        <div className="header__logo">
          <img src={logo} alt="WaiterApp Logo" />
        </div>
        <Link to='/' className='header__title'> WaiterApp </Link>
      </div>

      {authenticated && (
        <div className="header__menu">
          {user.type === 'waiter' ? <Link to='/signup/waiter' >Criar garçom</Link> : <CartShow />}
          <div className='header__logout-button' onClick={handleLogout}>
            <p>{user.nome}</p>
            <img src={logout} alt="Sair" />
          </div>
        </div>
      )}
    </header>
  )
}
