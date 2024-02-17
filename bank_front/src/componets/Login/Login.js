import '../../App.css';

import ConfirmModal from "../ConfirmModal/ConfirmModal";
import axios from 'axios';
import { useEffect, useState } from 'react';



function Login({setSessionCookie, setID}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
  
    const [showModal, setShowModal] = useState(false);
  
    const handleLogin = async () => {
  
      try {
          const response = await axios.get('http://localhost:8080/back_bank/login', {
            params:{
              username: username,
              password: password
            }
          });
  
          console.log(response)
  
          if (response.status === 200) {
  
              document.cookie = `JSESSIONID=${response.data}}; path=/back_bank`;
              setSessionCookie(response.data);
              setMessage(response.data);
              setShowModal(true);
  
              setID(username)
          } else {
              setMessage('로그인 실패');
              setShowModal(true);
          }
      } catch (error) {
          setMessage('로그인 실패');
          setShowModal(true);
      }
    };
  
    return (
      <div className='center'>
        <div className='deposit-container'>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="input-field"
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="input-field"
            />
            <button className="submit-button" onClick={handleLogin}>로그인</button>
            {/* <button onClick={handleProtectedResource}>보호된 리소스 요청</button> */}
            <p>{message}</p>
  
          {showModal && <ConfirmModal message={message} onClose={() => setShowModal(false)} />}
        </div>
      </div>
  );
  }

export default Login;