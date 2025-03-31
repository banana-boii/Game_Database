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
        {games.map((game) => (
          <li key={game.gameId}>
            <h3>{game.name}</h3>
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
