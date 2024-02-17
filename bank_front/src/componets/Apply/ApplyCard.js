import '../../App.css';
import { useEffect, useState } from 'react';

import axios from 'axios';

import ConfirmModal from '../ConfirmModal/ConfirmModal';

function ApplyCard({ sessionCookie }) {
  const [cardType, setCardType] = useState('credit');  // 초기값은 입출금

  const [accountNumber, setAccountNumber] = useState("")


  const [showModal, setShowModal] = useState(false);

  const applyCard = async () => {
      const response = await axios.get('http://localhost:8080/back_bank/createCard.do', {
        params:{
          cardType:cardType,
          accountId:accountNumber,
          JSESSIONID: sessionCookie
        }
      });
  };

  const handleSubmission = () => {

    try {
        applyCard()
      setShowModal(true);
    } catch (error) {
      console.error('There was a problem with the request:', error.message);
    }
  }

  return (
    <div className='center'>
      <div className="selectdiv">
        <p className='title'>카드종류 선택</p>
        <select
          id="cardType"
          value={cardType}
          onChange={(e) => setCardType(e.target.value)}
        >
          <option value="credit">신용</option>
          <option value="check">체크</option>
        </select>

        <input
              type="text"
              placeholder="account number"
              value={accountNumber}
              onChange={(e) => setAccountNumber(e.target.value)}
              className="input-field"
        />

        <button onClick={handleSubmission} className='button'>신청</button>

        {showModal && <ConfirmModal message="신청이 완료되었습니다" onClose={() => setShowModal(false)} />}
      </div>
    </div>

  );
}

export default ApplyCard;