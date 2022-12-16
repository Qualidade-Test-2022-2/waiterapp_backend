import { useState } from 'react';
import ProductModal from './components/ProductModal';
import './product.css'

export default function Product({ product }) {
  const [show, setShow] = useState(false)

  return (
    <>
      <ProductModal
        opened={show}
        onClose={() => setShow(false)}
        product={product}
      />
      <div className="product" onClick={() => setShow(true)}>
        <h3>{product.nome}</h3>
        <p className="product__description">{product.descricao}</p>
        <div className="product__container">
          <p className="product__price">R$ {product.preco.toFixed(2).replace('.', ',')}</p>
        </div>
      </div>
    </>
  );
}
