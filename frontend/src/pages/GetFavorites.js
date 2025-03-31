import React, { useState } from 'react';
import { getFavorites } from '../api';

function GetFavorites() {
  const [favorites, setFavorites] = useState([]);

  const fetchFavorites = async () => {
    try {
      const userId = localStorage.getItem('userId');
      const token = localStorage.getItem('token');
      if (!userId || !token) {
        alert('Session expired. Please log in again.');
        window.location.href = '/login'; // Redirect to login page
        return;
      }
      const data = await getFavorites(userId);
      setFavorites(data);
    } catch (error) {
      console.error('Error fetching favorites:', error);
      alert('Failed to fetch favorites. Please try again.');
    }
  };

  return (
    <div>
      <h2>Favorites</h2>
      <button onClick={fetchFavorites}>Load Favorites</button>
      <ul>
        {favorites.map((game) => (
          <li key={game.id}>{game.game.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default GetFavorites;
