import { useAuthentication } from '../../context/AuthContext'
import logo from '../../images/logo-waiterapp.png'
import logout from '../../images/logout.svg'
import './header.css'

export default function Header() {
  const { authenticated, user, handleLogout } = useAuthentication()

  return (
    <header className="header">
      <div className='header__info'>
        <div className="header__logo">
          <img src={logo} alt="WaiterApp Logo" />
        </div>
        <h1>WaiterApp</h1>
      </div>

      {authenticated && (
        <div className="header__menu">
          <div onClick={handleLogout}>
            <p>{user.nome}</p>
            <img src={logout} alt="Sair" />
          </div>
        </div>
      )}
    </header>
  )
}
