import '../../App.css';
import { useEffect, useState } from 'react';

import Account_history from './Account_history';


//계좌 블럭
//account block
function Account_Block({number, history, monney}){
    const [showHistory, setShowHistory] = useState(false);
  
    const handleMouseEnter = () => {
      setShowHistory(true);
    };
  
    const handleMouseLeave = () => {
      setShowHistory(false);
    };
  
    return(
    <div className='containerBox'
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
    >
      <a className='accountName'>계좌번호 {number}</a>
      <a className='accountMoney'> {monney}원 </a>
      <Account_history history={history} showHistory={showHistory}/>
  
    </div>)
  }

export default Account_Block;