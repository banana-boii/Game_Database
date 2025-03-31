import React, { useState } from 'react';
import { getUserLibrary } from '../api';

function GetLibrary() {
  const [userId, setUserId] = useState('');
  const [library, setLibrary] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await getUserLibrary(userId);
      setLibrary(data);
    } catch (error) {
      console.error('Error fetching library:', error);
    }
  };

  return (
    <div>
      <h2>Get Library</h2>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="User ID"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
        />
        <button type="submit">Get Library</button>
      </form>
      <ul>
        {library.map((savedGame) => (
          <li key={savedGame.id}>
            {savedGame.game.name} - {savedGame.game.description}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default GetLibrary;
