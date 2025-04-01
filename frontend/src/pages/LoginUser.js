import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginUser() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    console.log('Login button clicked'); // Debugging log
    console.log('Username:', username); // Debugging log
    console.log('Password:', password); // Debugging log

    try {
      const response = await fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      console.log('Response status:', response.status); // Debugging log

      if (!response.ok) {
        const errorText = await response.text();
        console.error('Error response from backend:', errorText); // Debugging log
        throw new Error(`Login failed: ${errorText}`);
      }

      const token = await response.text(); // Assuming the backend returns the token as plain text
      console.log('Token received:', token); // Debugging log
      localStorage.setItem('jwtToken', token); // Store the token in localStorage
      navigate('/get-library'); // Redirect to the library page
    } catch (err) {
      console.error('Login error:', err);
      setError(err.message);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default LoginUser;
