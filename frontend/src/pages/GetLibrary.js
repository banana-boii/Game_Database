import React, { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode'; // Use named export

function GetLibrary() {
  const [library, setLibrary] = useState([]);
  const [error, setError] = useState(null);

  const token = localStorage.getItem('jwtToken'); // Retrieve the token from localStorage
  let userId;
  try {
    userId = jwtDecode(token).userId; // Extract user_id from the token
    console.log('Extracted userId from token:', userId); // Debugging log
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
        console.log('Fetched library data:', data); // Debugging log
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
