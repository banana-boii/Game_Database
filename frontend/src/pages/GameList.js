import React, { useEffect, useState } from 'react';
import { fetchGames } from '../api';

function GameList() {
  const [games, setGames] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    const loadGames = async () => {
      try {
        const data = await fetchGames(page);
        console.log('Fetched games:', data); // Debugging
        setGames(data.content); // `content` contains the list of games
        setTotalPages(data.totalPages); // `totalPages` contains the total number of pages
      } catch (error) {
        console.error('Error fetching games:', error);
        alert('Failed to load games. Please try again.');
      }
    };

    loadGames();
  }, [page]);

  const handleNextPage = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
    }
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  return (
    <div>
      <h2>Game List</h2>
      <ul>
        {games.map(({ game, images, firstVideo }) => (
          <li key={game.gameId}>
            <h3>{game.name}</h3>
            {/* Debugging */}
            {console.log('Images for game:', game.name, images)}
            {/* Horizontal Scrollbar for Images */}
            <div style={{ display: 'flex', overflowX: 'auto', gap: '10px', paddingBottom: '10px' }}>
              {images && images.map((image, index) => (
                <img
                  key={index}
                  src={image}
                  alt={`${game.name} screenshot ${index + 1}`}
                  style={{
                    height: '300px', // Fixed height
                    width: `${300 * (16 / 9)}px`, // Maintain 16:9 aspect ratio
                    objectFit: 'cover',
                    borderRadius: '8px',
                  }}
                />
              ))}
            </div>
            {/* Video */}
            {firstVideo && (
              <video controls style={{ width: '100%', maxHeight: '300px', borderRadius: '8px' }}>
                <source src={firstVideo} type="video/mp4" />
                Your browser does not support the video tag.
              </video>
            )}
            {/* Game Details */}
            <p>Release Date: {game.releaseDate}</p>
            <p>Price: ${game.price}</p>
            <p>Short Description: {game.shortDescription}</p>
          </li>
        ))}
      </ul>
      <div>
        <button onClick={handlePreviousPage} disabled={page === 0}>
          Previous
        </button>
        <button onClick={handleNextPage} disabled={page === totalPages - 1}>
          Next
        </button>
      </div>
    </div>
  );
}

export default GameList;
