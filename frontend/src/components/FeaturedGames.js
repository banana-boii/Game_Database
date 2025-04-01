import React, { useState } from 'react';

function FeaturedGames({ games }) {
  const [currentIndex, setCurrentIndex] = useState(0);

  if (!games || games.length === 0) {
    return <div style={{ padding: '20px', color: '#fff' }}>No featured games available.</div>;
  }

  const handleNext = () => {
    if (currentIndex < games.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const handlePrevious = () => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
    }
  };

  const currentGame = games[currentIndex];

  return (
    <div style={{ display: 'flex', alignItems: 'center', padding: '20px', backgroundColor: '#1b2838', color: '#fff' }}>
      <button onClick={handlePrevious} disabled={currentIndex === 0} style={{ marginRight: '10px' }}>←</button>
      <div style={{ flex: 1, display: 'flex', gap: '20px' }}>
        <img src={currentGame.headerImage} alt={currentGame.name} style={{ width: '60%', borderRadius: '8px' }} />
        <div>
          <h3>{currentGame.name}</h3>
          <div style={{ display: 'flex', gap: '10px' }}>
            {currentGame.images.slice(0, 3).map((image, index) => (
              <img key={index} src={image} alt={`${currentGame.name} screenshot ${index + 1}`} style={{ width: '100px', borderRadius: '8px' }} />
            ))}
          </div>
        </div>
      </div>
      <button onClick={handleNext} disabled={currentIndex === games.length - 1} style={{ marginLeft: '10px' }}>→</button>
    </div>
  );
}

export default FeaturedGames;
