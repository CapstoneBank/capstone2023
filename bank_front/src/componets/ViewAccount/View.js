
import '../../App.css';
import axios from 'axios';
import { useEffect, useState } from 'react';

import Account_Block from './Account_Block';


//계좌조회
function View({sessionCookie}){
    const [account, setAccount] = useState([
        {
          number: "00000000000",
          money: 500000,
          history: [{"11111": "+50000"}]
        },
        {
          number: "2836819827983",
          money: 500000,
          history: [{"12233": "+50000"}, {"13246" : "-3200"}]
        },
    ])
  
    useEffect(() => {
      ///getAccountsByUserAndType.do
      const fetchData = async () => {
        try {
          const response = await axios.get('http://localhost:8080/back_bank/getAccountsByUserAndType.do', {
            params: {
              accountType: "inout",
              JSESSIONID: sessionCookie
            }
          });
          const res = [];
          for (const account of response.data) {
            const history = await axios.get('http://localhost:8080/back_bank/getTransactionHistory.do', {
              params: {
                accountId: account["number"],
                JSESSIONID: sessionCookie
              }
            });
  
  
            res.push({
              number: account["number"],
              money: account["money"],
              history: history.data
            });
          }
  
          setAccount(res);
        } catch (error) {
          console.error("API 요청 중 오류 발생:", error);
        }
      };
  
      fetchData();
  
    }, [])
    
    const accountList = account.map(obj=>
      <Account_Block history={obj.history} monney={obj.money} number={obj.number} key={obj.number}/>
    )
    return(
    <div className='container'>
      {accountList}
    </div>)
  }


  export default View;