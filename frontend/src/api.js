import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // Ensure this matches your backend URL

// Game API calls
export const fetchGames = async (page = 0, size = 10) => {
  const response = await axios.get(`${API_BASE_URL}/api/games/list`, {
    params: { page, size }, // Pass pagination parameters
  });
  return response.data;
};

export const fetchGameDetails = async (id) => {
  const response = await axios.get(`${API_BASE_URL}/games/${id}`);
  return response.data;
};

// User API calls
export const registerUser = async (userData) => {
  const response = await axios.post(`${API_BASE_URL}/api/users/register`, userData);
  return response.data;
};

export const loginUser = async (loginData) => {
  const response = await axios.post(`${API_BASE_URL}/api/users/login`, loginData);
  return response.data;
};



// In api.js
export const addReview = async (userId, gameId, reviewData) => {
  try {
    console.log('Sending review data:', {
      userId,
      gameId,
      rating: reviewData.rating,
      reviewText: reviewData.reviewText
    });
    
    const response = await fetch(`http://localhost:8080/api/reviews`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        userId: userId,
        gameId: gameId,
        rating: reviewData.rating,
        reviewText: reviewData.reviewText
      }),
    });
    
    if (!response.ok) {
      const errorText = await response.text();
      console.error('Server responded with error:', errorText);
      throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
    }
    
    return await response.text();
  } catch (error) {
    console.error('Error in addReview API call:', error);
    throw error;
  }
};


export const removeFavorite = async (userId, gameId) => {
  const response = await axios.delete(`${API_BASE_URL}/api/users/${userId}/favorites`, {
    params: { gameId },
  });
  return response.data;
};

export const getFavorites = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/api/users/${userId}/favorites`);
  return response.data;
};

export const getUserLibrary = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/api/users/${userId}/library`);
  return response.data;
};

export const getUserProfile = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/api/users/${userId}/profile`);
  return response.data;
};

export const addGameToLibrary = async (userId, gameId) => {
  const response = await axios.post(`${API_BASE_URL}/api/users/${userId}/library`, null, {
    params: { gameId },
  });
  return response.data;
};
