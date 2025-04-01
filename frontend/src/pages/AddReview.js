import React, { useState } from 'react';
import { addReview } from '../api';

function AddReview() {
  const [reviewData, setReviewData] = useState({ 
    userId: '', 
    gameId: '', 
    rating: '', 
    reviewText: '' 
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReviewData({ ...reviewData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    
    try {
      console.log('Submitting review:', reviewData);
      
      const response = await addReview(
        reviewData.userId, 
        reviewData.gameId, 
        { 
          rating: parseInt(reviewData.rating, 10), 
          reviewText: reviewData.reviewText 
        }
      );
      
      console.log('Response from server:', response);
      setSuccess('Review added successfully!');
      // Clear the form
      setReviewData({ userId: '', gameId: '', rating: '', reviewText: '' });
    } catch (error) {
      console.error('Error adding review:', error);
      setError(error.message || 'Failed to add review. Please try again.');
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', maxWidth: '500px', margin: '0 auto', gap: '10px' }}>
      <h2>Add Review</h2>
      
      {error && <div style={{ color: 'red', padding: '10px', backgroundColor: '#ffeeee', borderRadius: '4px' }}>{error}</div>}
      {success && <div style={{ color: 'green', padding: '10px', backgroundColor: '#eeffee', borderRadius: '4px' }}>{success}</div>}
      
      <div>
        <label htmlFor="userId">User ID:</label>
        <input 
          id="userId"
          name="userId" 
          value={reviewData.userId}
          placeholder="User ID" 
          onChange={handleChange} 
          required 
          style={{ width: '100%', padding: '8px' }}
        />
      </div>
      
      <div>
        <label htmlFor="gameId">Game ID:</label>
        <input 
          id="gameId"
          name="gameId" 
          value={reviewData.gameId}
          placeholder="Game ID" 
          onChange={handleChange} 
          required 
          style={{ width: '100%', padding: '8px' }}
        />
      </div>
      
      <div>
        <label htmlFor="rating">Rating (1-5):</label>
        <input 
          id="rating"
          name="rating" 
          type="number" 
          min="1" 
          max="5"
          value={reviewData.rating}
          placeholder="Rating (1-5)" 
          onChange={handleChange} 
          required 
          style={{ width: '100%', padding: '8px' }}
        />
      </div>
      
      <div>
        <label htmlFor="reviewText">Review:</label>
        <textarea 
          id="reviewText"
          name="reviewText" 
          value={reviewData.reviewText}
          placeholder="Write your review here..." 
          onChange={handleChange} 
          required 
          rows="5"
          style={{ width: '100%', padding: '8px' }}
        />
      </div>
      
      <button 
        type="submit" 
        style={{ 
          padding: '10px', 
          backgroundColor: '#4CAF50', 
          color: 'white', 
          border: 'none', 
          borderRadius: '4px', 
          cursor: 'pointer',
          marginTop: '10px'
        }}
      >
        Submit Review
      </button>
    </form>
  );
}

export default AddReview;
