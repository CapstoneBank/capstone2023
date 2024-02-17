
import '../../App.css';

//확인 메세지 모델
const ConfirmModal = ({ message, onClose }) => {
    return (
      <div className="confirm-modal-container">
        <div className="confirm-modal-content">
          <p>{message}</p>
          <button onClick={onClose} className="confirm-button">확인</button>
        </div>
      </div>
    );
  };
  

  export default ConfirmModal;