import './item.css'

export default function Item({ item }) {

  return (
    <div className="item">
      <h3>{item.nome}</h3>
      <p className="item__description">{item.descricao}</p>
      <div className="item__container">
        <p className="item__price">R$ {item.preco.toFixed(2).replace('.', ',')}</p>
      </div>
    </div>
  );
}
