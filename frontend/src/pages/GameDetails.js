import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchGameDetails } from '../api';

function GameDetails() {
  const { id } = useParams();
  const [game, setGame] = useState(null);

  useEffect(() => {
    const getGameDetails = async () => {
      try {
        const data = await fetchGameDetails(id);
        setGame(data);
      } catch (error) {
        console.error('Error fetching game details:', error);
      }
    };
    getGameDetails();
  }, [id]);

  if (!game) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <h2>{game.name}</h2>
      <p>{game.description}</p>
      <p>Release Date: {game.releaseDate}</p>
    </div>
  );
}

export default GameDetails;
