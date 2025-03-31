import React, { useState } from 'react';
import { addReview } from '../api';

function AddReview() {
  const [reviewData, setReviewData] = useState({ userId: '', gameId: '', content: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReviewData({ ...reviewData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await addReview(reviewData.userId, reviewData.gameId, { content: reviewData.content });
      alert(response);
    } catch (error) {
      console.error('Error adding review:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Review</h2>
      <input name="userId" placeholder="User ID" onChange={handleChange} required />
      <input name="gameId" placeholder="Game ID" onChange={handleChange} required />
      <textarea name="content" placeholder="Review Content" onChange={handleChange} required />
      <button type="submit">Add Review</button>
    </form>
  );
}

export default AddReview;
