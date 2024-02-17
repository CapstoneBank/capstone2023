import Home from './componets/Home/Home';
import Join from './componets/Join/Join';
import DepositSimulation from './componets/Deposit/DepositSimulation';
import Login from './componets/Login/Login';
import Menu from './componets/Menu/Menu';
import View from './componets/ViewAccount/View';
import Transfer from './componets/Transfer/Transfer';
import Card from './componets/Card/Card';
import ApplyAccount from './componets/Apply/ApplyAccount';
import ApplyCard from './componets/Apply/ApplyCard';
import SaveAccount from './componets/SaveAccount/SaveAccount';

import ConfirmModal from './componets/ConfirmModal/ConfirmModal';

import { useEffect, useState } from 'react';
import { BrowserRouter, Route, Link, Routes } from 'react-router-dom';
import './App.css';
import axios from 'axios';


function App() {
  //로그인 쿠키 저장예정
  const [sessionCookie, setSessionCookie] = useState(null)
  const [ID, setID] = useState("")
  const [card, setCard] = useState({})

  const userbar = () =>{
    if(ID != "")
      return ID
    else
    return (<><Link to="/join">회원가입</Link>
      {" "}
      <Link to="/login">로그인</Link></>)
  }

  return (
    <BrowserRouter>
      <nav>
        <div className='home'>
          <Link to="/">Capstone Bank</Link> 
        </div>
        <Menu/>
        <div className='loginButton'>
          {userbar()}
        </div>
      </nav>
      <main>
      </main>
      <Routes>
        <Route path="/" element={<Home cookie={sessionCookie}/>} />
        <Route path="/join" element={<Join/>} />
        <Route path="/login" element={<Login setSessionCookie={setSessionCookie} setID={setID}/>} />

        <Route path="/account" element={<View sessionCookie={sessionCookie}/>}/>
        <Route path="/transfer" element={<Transfer sessionCookie={sessionCookie} />} />
        <Route path="/savings" element={<SaveAccount sessionCookie={sessionCookie} />} />
        <Route path="/card" element={<Card sessionCookie={sessionCookie} />} />

        <Route path="/applyAccount" element={<ApplyAccount sessionCookie={sessionCookie}/>} />
        <Route path="/applyCard" element={<ApplyCard sessionCookie={sessionCookie}/>} />

        <Route path='/accountAdd' element={<DepositSimulation sessionCookie={sessionCookie}/>} />
        <Route path="/applySaving" element={<ApplySaving sessionCookie={sessionCookie}/>} />

      </Routes>
    </BrowserRouter>
  );
}

//로드 페이지 전환시
function loading(redi){

}


function accountAdd(){
  return 
}

//예금 기능 구현
function ApplySaving({sessionCookie}){

}

//메뉴 부분 컴포넌트

function Loan(){
  return(
  <div>
    대출화면
  </div>)
}


export default App;
