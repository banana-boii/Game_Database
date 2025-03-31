import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
    const logoutUser = async () => {
      try {
        const token = localStorage.getItem('token');
        if (token) {
          // Call the backend logout API
          await axios.post(
            'http://localhost:8080/api/users/logout',
            {},
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
        }

        // Remove the token from localStorage
        localStorage.removeItem('token');

        // Redirect to the login page
        navigate('/login');
      } catch (error) {
        console.error('Error during logout:', error);
        // Redirect to the login page even if the logout API fails
        navigate('/login');
      }
    };

    logoutUser();
  }, [navigate]);

  return <p>Logging out...</p>;
}

export default Logout;
