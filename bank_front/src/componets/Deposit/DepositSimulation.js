import '../../App.css';
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import { useEffect, useState } from 'react';

import axios from 'axios';


//입금
const DepositSimulation = ({sessionCookie}) => {
    const [account_id, setAccountId] = useState('');
    const [money, setMoney] = useState('');
  
    const [showModal, setShowModal] = useState(false);
  
    const handleDeposit = async () => {
      try {
        const response = await axios.get('http://localhost:8080/back_bank/depositAccounts.do', {
          params: {
            account_id: account_id,
            money: money,
            JSESSIONID: sessionCookie
          }
        });
        // handle response data or set state as needed
  
        setShowModal(true);
      } catch (error) {
        console.error('Error making the deposit', error);
      }
    };
  
    return (
      <div className='center'>
      <div className="deposit-container">
        <input
          type="text"
          placeholder="Account ID"
          value={account_id}
          onChange={e => setAccountId(e.target.value)}
          className="input-field"
        />
        <input
          type="number"
          placeholder="Money"
          value={money}
          onChange={e => setMoney(e.target.value)}
          className="input-field"
        />
        <button onClick={handleDeposit} className="submit-button">입금</button>
      </div>
      {showModal && <ConfirmModal message="입금이 완료되었습니다." onClose={() => setShowModal(false)} />}
      </div>
    );
  };

  export default DepositSimulation;