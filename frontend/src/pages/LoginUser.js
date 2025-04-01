import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/login.css';
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

      const token = await response.text(); // Assuming the backend returns the token as plain text
      localStorage.setItem('jwtToken', token); // Store the token in localStorage
      navigate('/get-library'); // Redirect to the library page
    } catch (err) {
      console.error('Login error:', err);
      setError(err.message);
    }
  };

  return (
    <div className="form"> {/* Use the 'form' class from login.css */}
    <h2 id="heading">Login</h2> {/* Use the 'heading' ID from login.css */}
    <form onSubmit={handleLogin}>
      <div className="field"> {/* Use the 'field' class from login.css */}
        <input
          type="text"
          className="input-field" // Use the 'input-field' class from login.css
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username" // Added placeholder for better UI
          required
        />
      </div>
      <div className="field"> {/* Use the 'field' class from login.css */}
        <input
          type="password"
          className="input-field" // Use the 'input-field' class from login.css
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password" // Added placeholder for better UI
          required
        />
      </div>
      <div className="btn"> {/* Use the 'btn' class from login.css */}
        <button type="submit" className="button2">Login</button> {/* Use the 'button2' class from login.css */}
      </div>
    </form>
    {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}
  </div>
  );
}

export default LoginUser;
