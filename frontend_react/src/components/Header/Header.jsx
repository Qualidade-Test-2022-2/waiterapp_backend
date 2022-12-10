import logo from '../../images/logo-waiterapp.png'
import './header.css'

export default function Header() {

  return (
    <header className="header">
      <div className='header__info'>
        <div className="header__logo">
          <img src={logo} alt="WaiterApp Logo" />
        </div>
        <h1>WaiterApp</h1>
      </div>

    </header>
  )
}
