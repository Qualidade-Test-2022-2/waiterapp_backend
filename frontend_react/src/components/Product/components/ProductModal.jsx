import Modal from "../../Modal/Modal";
import addCart from "../../../images/add_cart.svg";
import { useState } from "react";
import { useCartContext } from "../../../context/CartContext";

export default function ProductModal({ opened, onClose, product }) {
  const [count, setCount] = useState(1)
  const { addToCart } = useCartContext()

  const handleSubmit = (event) => {
    event.preventDefault();
    addToCart(product, count);
  }


  return (
    <Modal opened={opened} onClose={onClose} >
      <div className="product-modal">
        <div className="product-modal__body">
          <h3>{product.nome}</h3>
          <p>{product.descricao}</p>
        </div>
        <div className="product-modal__footer">
          <p className="product__price">R$ {product.preco.toFixed(2).replace('.', ',')}</p>
          <form className="product-modal__actions" onSubmit={handleSubmit}>
            <div className="product-modal__form">
              <label>Quantidade: </label>
              <input
                type='number'
                value={count}
                onChange={(e) => setCount(e.target.value)}
                className="product-modal__count"
                min={1}
              />
            </div>
            <input
              type='image'
              src={addCart}
              alt='Adicionar ao carrinho'
              className="button__primary addCartButton"
            />
          </form>
        </div>
      </div>
    </Modal>
  )
}
