import '../../App.css';
import { useEffect, useState } from 'react';



function Account_history({history, showHistory}){
    const historyList = history.map(obj=>
      <li className='historyBox'>{Object.keys(obj)[0]} {obj[Object.keys(obj)[0]]}원</li>
      // <li className='historyBox'>{x}</li>
    )
    const [css, setCss] = useState(
      {
        overflow: 'auto',
        width: "100%",
        height: "0px",
        transition: 'width 0.5s ease-out, height 0.5s ease-out'
      }
    )
    useEffect(()=>{
      if(showHistory){
        setCss({
          ...css,
          width: "100%",
          height: "200px",
        })
      }else{
        setCss({
          ...css,
          width: "100%",
          height: "0px",
        })
      }
    },[showHistory])
  
  
    return(
    <div className='accountHistory' style={css}>
      <ul> {historyList} </ul>
    </div>)
  }

export default Account_history;
