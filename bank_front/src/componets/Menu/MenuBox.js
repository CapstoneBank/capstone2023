import '../../App.css';

import ChidMenu from "./ChidMenu";

import { useEffect, useState } from 'react';
import { BrowserRouter, Route, Link, Routes } from 'react-router-dom';


function MenuBox({path, childs, menuname}){
    const [showChildMenu, setShowChildMenu] = useState(false);
  
    const handleMouseEnter = () => {
      setShowChildMenu(true);
    };
  
    const handleMouseLeave = () => {
      setShowChildMenu(false);
    };
  
    //부모메뉴바
    return (
      <li className='MenuBox'
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <Link to={path}>{menuname}</Link>
        <ChidMenu
          path={path}
          childList={childs}
          show={showChildMenu}
        />
      </li>)
}

export default MenuBox;