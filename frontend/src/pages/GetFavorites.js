import React, { useEffect, useState } from 'react';

function GetFavorites({ userId, token }) {
  const [favorites, setFavorites] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchFavorites = async () => {
      try {
        const response = await fetch(`/api/users/${userId}/favorites`, {
          headers: {
            Authorization: `Bearer ${token}`, // Include the JWT token in the Authorization header
          },
        });
        if (!response.ok) {
          const errorText = await response.text();
          console.error('Error response from backend:', errorText);
          throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        const data = await response.json(); // Parse the JSON response
        setFavorites(data);
      } catch (err) {
        console.error('Error fetching favorites:', err);
        setError(err.message);
      }
    };

    fetchFavorites();
  }, [userId, token]);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h2>Your Favorites</h2>
      <ul>
        {favorites.map((savedGame, index) => (
          <li key={index}>
            <h3>{savedGame.game.name}</h3>
            <img
              src={savedGame.headerImage}
              alt={`${savedGame.game.name} header`}
              style={{ width: '200px', borderRadius: '8px' }}
            />
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GetFavorites;
