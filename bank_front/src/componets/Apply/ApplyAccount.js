import '../../App.css';
import { useEffect, useState } from 'react';

import axios from 'axios';

import ConfirmModal from '../ConfirmModal/ConfirmModal';


function ApplyAccount({ sessionCookie }) {
  const [accountType, setAccountType] = useState('inout');  // 초기값은 입출금


  const [showModal, setShowModal] = useState(false);

  const applyAccount = async () => {
    //const endpoint = `http://localhost:8080/back_bank/createAccount.do?s=web&accountType=${accountType}`;
      //document.cookie = `JSESSIONID=${sessionCookie}}; path=/back_bank`;
      const response = await axios.get('http://localhost:8080/back_bank/createAccount.do', {
        params:{
          accountType: accountType,
          JSESSIONID: sessionCookie
        }
      });
  };

  const apply_dAccount = async () => {
    const response = await axios.get('http://localhost:8080/back_bank/applyDepositAccounts.do', {
        params:{
          JSESSIONID: sessionCookie
        }
      })
  }
  const handleSubmission = () => {

    try {
      if(accountType == "inout"){
        applyAccount()
      }
      else if(accountType == "save"){
        apply_dAccount();
      }
      setShowModal(true);
    } catch (error) {
      console.error('There was a problem with the request:', error.message);
    }
  }

  return (
    <div className='center'>
      <div className="selectdiv">
        <p className='title'>계좌종류 선택</p>
        <select
          id="accountType"
          value={accountType}
          onChange={(e) => setAccountType(e.target.value)}
        >
          <option value="inout">입출금</option>
          <option value="save">예금</option>
        </select>
        <button onClick={handleSubmission} className='button'>신청</button>

        {showModal && <ConfirmModal message="신청이 완료되었습니다" onClose={() => setShowModal(false)} />}
      </div>
    </div>

  );
}

export default ApplyAccount;