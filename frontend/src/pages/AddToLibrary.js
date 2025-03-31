import React, { useState } from 'react';
import { addGameToLibrary } from '../api'; // Ensure this import is correct

function AddToLibrary() {
  const [data, setData] = useState({ userId: '', gameId: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setData({ ...data, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await addGameToLibrary(data.userId, data.gameId);
      alert(response);
    } catch (error) {
      console.error('Error adding game to library:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Game to Library</h2>
      <input name="userId" placeholder="User ID" onChange={handleChange} required />
      <input name="gameId" placeholder="Game ID" onChange={handleChange} required />
      <button type="submit">Add to Library</button>
    </form>
  );
}

export default AddToLibrary;
