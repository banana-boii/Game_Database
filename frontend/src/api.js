import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // Ensure this matches your backend URL

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  if (!token) {
    throw new Error('No token found in localStorage');
  }
  return { Authorization: `Bearer ${token}` };
};

// Game API calls
export const fetchGames = async () => {
  const response = await axios.get(`${API_BASE_URL}/games`);
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
  try {
    const response = await axios.post(`${API_BASE_URL}/api/users/login`, loginData);
    return response.data; // Return the token
  } catch (error) {
    console.error('Error in loginUser API call:', error);
    throw error;
  }
};

export const addReview = async (userId, gameId, reviewData) => {
  const response = await axios.post(`${API_BASE_URL}/api/users/${userId}/reviews`, reviewData, {
    params: { gameId },
  });
  return response.data;
};

export const addFavorite = async (userId, gameId) => {
  const response = await axios.post(`${API_BASE_URL}/api/users/${userId}/favorites`, null, {
    params: { gameId },
    headers: getAuthHeaders(),
  });
  return response.data;
};

export const removeFavorite = async (userId, gameId) => {
  const response = await axios.delete(`${API_BASE_URL}/api/users/${userId}/favorites`, {
    params: { gameId },
    headers: getAuthHeaders(),
  });
  return response.data;
};

export const getFavorites = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/api/users/${userId}/favorites`, {
    headers: getAuthHeaders(),
  });
  return response.data;
};

export const getUserLibrary = async (userId) => {
  const response = await axios.get(`${API_BASE_URL}/api/users/${userId}/library`, {
    headers: getAuthHeaders(),
  });
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
