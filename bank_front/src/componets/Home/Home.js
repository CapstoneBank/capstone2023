import '../../App.css';
import { useEffect, useState } from 'react';

//홈화면
function Home(){
    return(
    <div>
      <BannerComponent/>
    </div>)
}

function BannerComponent() {
    const [currentImageIndex, setCurrentImageIndex] = useState(1);
  
    const images = [
      "https://i.ibb.co/2YTNxCj/2023-08-28-012130.png",
      "https://i.ibb.co/QdvK7w2/Kakao-Talk-20230827-193323521.jpg",
      "https://i.ibb.co/YhfPm8p/Kakao-Talk-20230827-193326823.jpg"
      // ... 기타 이미지 URL들
    ];
  
    useEffect(() => {
      const interval = setInterval(() => {
        setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
      }, 5000); 
  
      return () => clearInterval(interval);
    }, []);
  
    return (
      <div className="banner-container back">
        {images.map((image, index) => (
          <img
            key={index}
            src={image}
            alt={`배너 이미지 ${index + 1}`}
            className={`banner-image ${currentImageIndex === index ? 'active' : ''}`}
          />
        ))}
      </div>
    );
  }
  
export default Home;