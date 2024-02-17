import '../../App.css';
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import axios from 'axios';
import { useEffect, useState } from 'react';

function Join(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [showModal, setShowModal] = useState(false);
  
    const handleLogin = async () => {
  
      try {
          const response = await axios.get('http://localhost:8080/back_bank/join.do',{
            params:{
              username: username,
              password: password
            }
          });
  
          if (response.status === 200) {
              setShowModal(true)
              setMessage('회원가입 성공 성공');
          } else {
            setShowModal(true)
            setMessage('회원가입 실패');
          }
      } catch (error) {
        setShowModal(true)  
        setMessage('회원가입 실패');
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
            <button className="submit-button" onClick={handleLogin}>회원가입</button>
        </div>
  
        {showModal && <ConfirmModal message={message} onClose={() => setShowModal(false)} />}
      </div>
    )
  }

export default Join;
  