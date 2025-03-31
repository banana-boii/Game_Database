import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { fetchGames } from '../api';

function GameList() {
  const [games, setGames] = useState([]);

  useEffect(() => {
    const getGames = async () => {
      const data = await fetchGames();
      setGames(data);
    };
    getGames();
  }, []);

  return (
    <div>
      <h2>Game List</h2>
      <ul>
        {games.map((game) => (
          <li key={game.id}>
            <Link to={`/games/${game.id}`}>{game.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GameList;
