import './modal.css'

export default function Modal({ children, opened, onClose }) {
  if (!opened) {
    return null
  }

  return (
    <div className="overlay-modal">
      <div className="modal" >
        <p className='modal__close-button' onClick={onClose}>X</p>
        <div className="modal__body">
          {children}
        </div>
      </div>
    </div>
  )
}
