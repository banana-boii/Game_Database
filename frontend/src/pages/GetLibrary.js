import React, { useEffect, useState } from 'react';

function GetLibrary({ userId, token }) {
  const [library, setLibrary] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log('Fetching library for userId:', userId); // Debugging log
    if (!userId) {
      setError('User ID is undefined');
      return;
    }

    const fetchLibrary = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/users/${userId}/library`, {
          method: 'GET',
          mode: 'cors', // Ensure the request is sent in CORS mode
          headers: {
            Authorization: `Bearer ${token}`, // Include the JWT token in the Authorization header
            'Content-Type': 'application/json', // Specify JSON content type
          },
          credentials: 'include', // Include cookies or credentials
        });
        if (!response.ok) {
          const errorText = await response.text();
          console.error('Error response from backend:', errorText);
          throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        const data = await response.json(); // Parse the JSON response
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
    <div>
      <h2>Your Library</h2>
      <ul>
        {library.map((savedGame, index) => (
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

export default GetLibrary;
