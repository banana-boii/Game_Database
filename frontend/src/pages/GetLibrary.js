import React, { useEffect, useState } from 'react';

function GetLibrary({ userId, token }) {
  const [library, setLibrary] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchLibrary = async () => {
      try {
        const response = await fetch(`/api/users/${userId}/library`, {
          headers: {
            Authorization: `Bearer ${token}`, // Include the JWT token in the Authorization header
          },
        });
        if (!response.ok) {
          const errorText = await response.text(); // Get the error message from the response
          throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        const data = await response.json();
        setLibrary(data);
      } catch (err) {
        console.error('Error fetching library:', err);
        setError(err.message); // Display the error message
      }
    };

    fetchLibrary();
  }, [userId, token]);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h2>Your Library</h2>
      <ul>
        {library.map((savedGame, index) => {
          // Handle cases where savedGame.game is undefined
          if (!savedGame.game) {
            return (
              <li key={index} style={{ color: 'red' }}>
                Error: Game data is missing for this entry.
              </li>
            );
          }

          return (
            <li key={savedGame.game.gameId}>
              <h3>{savedGame.game.name}</h3>
              <img
                src={savedGame.game.headerImage}
                alt={`${savedGame.game.name} header`}
                style={{ width: '200px', borderRadius: '8px' }}
              />
              <p>Saved At: {new Date(savedGame.savedAt).toLocaleString()}</p>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

export default GetLibrary;
