import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginUser.css'; // Make sure to create this CSS file in the same directory

function LoginUser() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Login failed: ${errorText}`);
      }

      const token = await response.text();
      localStorage.setItem('jwtToken', token);
      navigate('/get-library');
    } catch (err) {
      console.error('Login error:', err);
      setError(err.message);
    }
  };

  return (
    <div className="form-container">
      <form className="form" onSubmit={handleLogin}>
        <p className="title">Login</p>
        <p className="message">Login to access your game library.</p>
        <label>
          <input
            className="input"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            placeholder=""
          />
          <span>Username</span>
        </label>
        <label>
          <input
            className="input"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            placeholder=""
          />
          <span>Password</span>
        </label>
        <button className="submit" type="submit">Login</button>
        {error && <p className="error-message">{error}</p>}
        <p className="signin">
          Don't have an account? <a href="/register">Register</a>
        </p>
      </form>
    </div>
  );
}

export default LoginUser;