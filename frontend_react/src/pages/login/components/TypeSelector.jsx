export default function TypeSelector({ value, onChange }) {
  return (
    <div className="login__type-selector">
      <div
        className={`login__type-selector__item${value === 'client' ? " selected": ""}`}
        onClick={() => onChange('client')}
      >
        Cliente
      </div>
      <div
        className={`login__type-selector__item${value === 'waiter' ? " selected" : ""}`}
        onClick={() => onChange('waiter')}
      >
        Garçom
      </div>
    </div>
  )
}
