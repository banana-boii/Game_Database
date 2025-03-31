import React, { useState } from 'react';
import { addFavorite } from '../api';

function AddFavorite() {
  const [data, setData] = useState({ userId: '', gameId: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setData({ ...data, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await addFavorite(data.userId, data.gameId);
      alert(response);
    } catch (error) {
      console.error('Error adding favorite:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Favorite</h2>
      <input name="userId" placeholder="User ID" onChange={handleChange} required />
      <input name="gameId" placeholder="Game ID" onChange={handleChange} required />
      <button type="submit">Add Favorite</button>
    </form>
  );
}

export default AddFavorite;
