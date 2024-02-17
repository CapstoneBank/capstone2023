import '../../App.css';
import { useEffect, useState } from 'react';
import { BrowserRouter, Route, Link, Routes } from 'react-router-dom';


//자식 메뉴
function ChidMenu({path, childList, show}){
    const [displayCss, setDisplayCss] = useState({display: 'none'})
    
    const menuList = Object.keys(childList).map(x => 
      <li className="childMenuBox" key={`${path}${childList[x]}`}>
        <Link to={`${childList[x]}`}>{x}</Link>
      </li>
    )
  
    useEffect(()=>{
      if(show){
        setDisplayCss({display: 'block'})
      }
      else{
        setDisplayCss({display: 'none'})
      }
  
    },[show])
  
    return (
      <div className='ChildMenu' style={displayCss}>
        <ul>
          {menuList}
        </ul>
      </div>)
  }

  export default ChidMenu;