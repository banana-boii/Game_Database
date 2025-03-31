import React, { useState } from 'react';
import { loginUser } from '../api';
import '../styles/FormStyles.css'; // Import the shared CSS file

function LoginUser() {
  const [loginData, setLoginData] = useState({ username: '', password: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log('Submitting login data:', loginData); // Debugging
      const token = await loginUser(loginData);
      console.log('Received token:', token); // Debugging
      localStorage.setItem('token', token); // Save the token in localStorage
      alert('Login successful!');
      window.location.href = '/'; // Redirect to the home page
    } catch (error) {
      console.error('Error logging in:', error);
      alert('Login failed. Please check your credentials.');
    }
  };

  return (
    <div>
      <div className="page-background"></div> {/* Blurred background */}
      <div className="form-container">
        <h2 className="title">Login</h2>
        <form className="form" onSubmit={handleSubmit}>
          <div className="input-group">
            <label htmlFor="username">Username</label>
            <input id="username" name="username" type="text" onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label htmlFor="password">Password</label>
            <input id="password" name="password" type="password" onChange={handleChange} required />
          </div>
          <button className="sign" type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}

export default LoginUser;
