import '../../App.css';
import { useEffect, useState } from 'react';

import axios from 'axios';

function Card({ sessionCookie}) {
  const [cards, setCards] = useState([])
  const [currentIndex, setCurrentIndex] = useState(0);
  const [currentCardType, setCurrentCardType] = useState(null);
  const [currentCardImage, setCurrentCardImage] = useState(null);

  useEffect(() => {
    const fetchCards = async (cardNumber) => {
      const response = await axios.get('http://localhost:8080/back_bank/viewCard.do', {
        params:{
          JSESSIONID: sessionCookie
        }
      });

      console.log(response.data)

      setCards(response.data);
    };

    fetchCards()

    if(cards.length > 0){
      if(cards[currentIndex]['ctype'] == "credit"){
        setCurrentCardImage("https://i.ibb.co/wrG7SLD/Kakao-Talk-20230827-195019490.jpg")
      }else{
        setCurrentCardImage("https://i.ibb.co/JQ3hn3f/Kakao-Talk-20230827-195023926.jpg")
      }
    }
  }, [])

  useEffect(() => {
    if(cards.length > 0){
      if(cards[currentIndex]['ctype'] == "credit"){
        setCurrentCardImage("https://i.ibb.co/wrG7SLD/Kakao-Talk-20230827-195019490.jpg")
      }else{
        setCurrentCardImage("https://i.ibb.co/JQ3hn3f/Kakao-Talk-20230827-195023926.jpg")
      }
    }
    
  }, [currentIndex, currentCardType]);

  return (
    <div className="card-container">
      {currentCardImage && cards[currentIndex].cid }
      {currentCardImage && <img src={currentCardImage} alt="Card"/>}
      <div>
        <button onClick={() => setCurrentIndex(prev => (prev > 0 ? prev - 1 : cards.length - 1))}>이전</button>
        <button onClick={() => setCurrentIndex(prev => (prev < cards.length - 1 ? prev + 1 : 0))}>다음</button>
      </div>
    </div>
  );
}

export default Card;