
import { useEffect, useState } from 'react';
import MenuBox from './MenuBox';

function Menu(){
    const [menu, setMenu] = useState({
      "조회": "/account",
      "이체": "/transfer",
      "예금": "/savings",
      "신청": "/applyAccount"
    });
  
    const [child, setChild] = useState({
      "/account": {"카드조회":"/card", "계좌조회":"/account"},
      "/transfer":{"계좌이체":"/account", "입금": "/accountAdd"},
      "/savings": {"예금통장": "/savings"},
      "/applyAccount": {"계좌신청": "/applyAccount", "카드신청": "/applyCard"}
    })
  
    //css 사용해서 menuBox a { display: block;}
    const menuList = Object.keys(menu).map(x => 
      <MenuBox path={menu[x]} childs={child[menu[x]]} menuname={x} key={menu[x]}/>
    )
    return (
    <div className='menuContainer'>
      <ul className='menu'>
        {menuList}
      </ul>
    </div>)
  
}

export default Menu;
  