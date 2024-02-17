import '../../App.css';
import { useEffect, useState } from 'react';

import ConfirmModal from '../ConfirmModal/ConfirmModal';


function SaveAccountBlock({number, endDate, money}){
  const [showModal, setShowModal] = useState(false);

  const handleSubmission = () => {
    setShowModal(true);
  }
  return (<div className='containerBox'>
      계좌번호 {number}<br/>
      금액 {money}원<br/>
      만기일 {endDate}<br/>
      <input type="text" name="acccount_id"/>
      <button onClick={handleSubmission} >확인</button>
      {showModal && <ConfirmModal message="해지기간이 아닙니다" onClose={() => setShowModal(false)} />}
  </div>)
}

export default SaveAccountBlock;