import { useCartContext } from '../../../context/CartContext'
import cartLogo from '../../../images/cart.svg'

export default function CartShow() {
  const { cart } = useCartContext()
  return (
    <div className="header__cart-show">
      <p>R$ {cart.totalPrice.toFixed(2).replace('.', ',')}</p>
      <img src={cartLogo} alt="Carrinho" />
    </div>
  )
}
