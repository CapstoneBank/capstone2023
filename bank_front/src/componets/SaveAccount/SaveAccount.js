import '../../App.css';
import { useEffect, useState } from 'react';

import axios from 'axios';

import SaveAccountBlock from './SaveAccountBlock';

//예금
function SaveAccount({sessionCookie}){
  const [Account, setAccount] = useState({
  })


  const getDepAccountList = async () => {
    const response = await axios.get('http://localhost:8080/back_bank/viewDepositAccounts.do', {
        params:{
          JSESSIONID: sessionCookie
        }
    })

    for(let data of response.data){
      setAccount(prevAccount => ({
        ...prevAccount,
        [data.a_id]:{
          "money": data.money,
          "date" : data.date,
        }
      }));

    }
  }

  useEffect(() =>{
    //불러오기
    getDepAccountList()
  }, [])

  const Accounts = () => Object.keys(Account).map(e => {

    return <SaveAccountBlock number={e} endDate={Account[e].date} money={Account[e].money}/>
  })

  return(<div className='container'>
    {Accounts()}
  </div>)
}

export default SaveAccount;