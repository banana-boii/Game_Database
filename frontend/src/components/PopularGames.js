import React from 'react';

function PopularGames({ games }) {
  return (
    <div style={{ display: 'flex', gap: '10px', overflowX: 'auto', padding: '20px', backgroundColor: '#1b2838' }}>
      {games.slice(0, 8).map((game) => (
        <img key={game.gameId} src={game.headerImage} alt={game.name} style={{ width: '150px', borderRadius: '8px' }} />
      ))}
    </div>
  );
}

export default PopularGames;
