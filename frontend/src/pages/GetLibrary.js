import React, { useState } from 'react';
import { getUserLibrary } from '../api';

function GetLibrary() {
  const [library, setLibrary] = useState([]);

  const fetchLibrary = async () => {
    try {
      const userId = localStorage.getItem('userId'); // Retrieve userId from localStorage
      if (!userId) {
        alert('User ID not found. Please log in again.');
        return;
      }
      console.log('Fetching library for userId:', userId); // Debugging
      const data = await getUserLibrary(userId);
      console.log('Library data:', data); // Debugging
      setLibrary(data);
    } catch (error) {
      console.error('Error fetching library:', error);
      alert('Failed to fetch library. Please try again.');
    }
  };

  return (
    <div>
      <h2>Library</h2>
      <button onClick={fetchLibrary}>Load Library</button>
      <ul>
        {library.map((game) => (
          <li key={game.id}>{game.game.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default GetLibrary;
