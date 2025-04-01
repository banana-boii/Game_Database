import React, { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import './GetLibrary.css'; // Make sure to create this CSS file in the same directory

function GetLibrary() {
  const [library, setLibrary] = useState([]);
  const [error, setError] = useState(null);

  const token = localStorage.getItem('jwtToken');
  let userId;
  try {
    userId = jwtDecode(token).userId;
    console.log('Extracted userId from token:', userId);
  } catch (err) {
    console.error('Error decoding token:', err);
    setError('Invalid token');
  }

  useEffect(() => {
    if (!userId) {
      setError('User ID is undefined');
      return;
    }

    const fetchLibrary = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/users/${userId}/library`, {
          method: 'GET',
          mode: 'cors',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          credentials: 'include',
        });
        if (!response.ok) {
          const errorText = await response.text();
          console.error('Error response from backend:', errorText);
          throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        const data = await response.json();
        console.log('Fetched library data:', data);
        setLibrary(data);
      } catch (err) {
        console.error('Error fetching library:', err);
        setError(err.message);
      }
    };

    fetchLibrary();
  }, [userId, token]);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="library-container">
      <h2>Your Library</h2>
      <div className="game-grid">
        {library.map((savedGame, index) => (
          <div className="card" key={index}>
            <div className="card2">
              <img
                src={savedGame.headerImage}
                alt={`${savedGame.game.name} header`}
                className="game-image"
              />
              <h3 className="game-title">{savedGame.game.name}</h3>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default GetLibrary;