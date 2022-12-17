import { useState } from 'react';
import { useAuthentication } from '../../context/AuthContext';
import ProductModal from './components/ProductModal';
import './product.css'

export default function Product({ product }) {
  const [show, setShow] = useState(false)
  const { user } = useAuthentication()

  const handleToggle = () => {
    if (user.type !== 'client') return

    setShow((prev) => !prev)
  }

  return (
    <>
      <ProductModal
        opened={show}
        onClose={handleToggle}
        product={product}
      />
      <div className={`product${user.type === 'waiter' && ' product__blocked'}`} onClick={handleToggle}>
        <h3>{product.nome}</h3>
        <p className="product__description">{product.descricao}</p>
        <div className="product__container">
          <p className="product__price">R$ {product.preco.toFixed(2).replace('.', ',')}</p>
        </div>
      </div>
    </>
  );
}
