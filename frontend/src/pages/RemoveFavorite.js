import React, { useState } from 'react';
import { removeFavorite } from '../api'; // Ensure this import is correct

function RemoveFavorite() {
  const [data, setData] = useState({ userId: '', gameId: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setData({ ...data, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await removeFavorite(data.userId, data.gameId);
      alert(response);
    } catch (error) {
      console.error('Error removing favorite:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Remove Favorite</h2>
      <input name="userId" placeholder="User ID" onChange={handleChange} required />
      <input name="gameId" placeholder="Game ID" onChange={handleChange} required />
      <button type="submit">Remove Favorite</button>
    </form>
  );
}

export default RemoveFavorite;
