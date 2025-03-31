import React, { useState } from 'react';
import { getFavorites } from '../api';

function GetFavorites() {
  const [userId, setUserId] = useState('');
  const [favorites, setFavorites] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await getFavorites(userId);
      setFavorites(data);
    } catch (error) {
      console.error('Error fetching favorites:', error);
    }
  };

  return (
    <div>
      <h2>Get Favorites</h2>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="User ID"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
        />
        <button type="submit">Get Favorites</button>
      </form>
      <ul>
        {favorites.map((savedGame) => (
          <li key={savedGame.id}>
            {savedGame.game.name} - {savedGame.game.description}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GetFavorites;
