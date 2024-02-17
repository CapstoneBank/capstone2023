import '../../App.css';
import { useEffect, useState } from 'react';

import axios from 'axios';

import ConfirmModal from '../ConfirmModal/ConfirmModal';

function Transfer({sessionCookie }) {

    const [accountIds, setAccountIds] = useState([]);
    const [selectedAccountId, setSelectedAccountId] = useState("");
    const [receiverAccountId, setReceiverAccountId] = useState("");
    const [amount, setAmount] = useState(0);
    const [balance, setBalance] = useState(null);
  
    const [message, setMessage] = useState("");
    const [showModal, setShowModal] = useState(false);
  
  
    const fetchData = async () => {
  
      const response = await axios.get('http://localhost:8080/back_bank/getAccountsByUserAndType.do', {
          params: {
            accountType: "inout",
            JSESSIONID: sessionCookie
          }
        });
  
      const ids = [];
      for (const account of response.data){
        ids.push(account["number"])
      }
      setAccountIds(ids)
      setSelectedAccountId(ids[0]);
    };
  
    const handleTransfer = async () => {
      try {
        const response = await axios.get('http://localhost:8080/back_bank/transferMoney.do', {
          params:{
            fromAccountId: selectedAccountId,
            toAccountId: receiverAccountId,
            amount: amount,
            JSESSIONID: sessionCookie
          }
        });
        if(response.status == 200) {
          setMessage("이체완료")
          setShowModal(true);
        } else {
          setShowModal(true);
          setMessage("이체실패" + response.data.message)
        }
      } catch (error) {
        setShowModal(true);
        setMessage('서버 에러: ' + error.message);
      }
    };
  
    const fetchBalance = async () => {
      try {
        console.log("현재 선택:", selectedAccountId)
        // 임시로 사용하는 API URL입니다. 실제 URL로 변경해주세요.
        const response = await axios.get('http://localhost:8080/back_bank/getAccountBalance.do',
        {
          params: {
            accountId: selectedAccountId,
            JSESSIONID: sessionCookie
          }
        });
  
        setBalance(response.data);
      } catch (error) {
        alert('잔액 조회 실패: ' + error.message);
      }
    };
  
    useEffect(() => {
      fetchData();
    }, []);
  
    useEffect(() => {
      
      if(selectedAccountId != ""){
        fetchBalance();
      }
    }, [selectedAccountId]);
  
    return (
      <div className='center'>
      <div className='deposit-container '>
       
        <div className='selectdiv'>
          <label>계좌 선택 </label>
          <select value={selectedAccountId} onChange={e => setSelectedAccountId(e.target.value)}>
            {accountIds.map(id => <option key={id} value={id}>{id}</option>)}
          </select>
        </div>
  
        <p>계좌 잔액: {balance}</p>
  
        <div>
          <input 
            type="text" 
            placeholder="이체할 계좌 번호" 
            value={receiverAccountId} 
            onChange={e => setReceiverAccountId(e.target.value)} 
          />
        </div>
  
        <div>
          <input 
            type="number" 
            placeholder="이체 금액" 
            value={amount} 
            onChange={e => setAmount(e.target.value)}
          />
        </div>
  
        <button className='submit-button' onClick={handleTransfer}>이체</button>
  
  
        {showModal && <ConfirmModal message={message} onClose={() => setShowModal(false)} />}
  
      </div>
      </div>
    );
  }

export default Transfer;