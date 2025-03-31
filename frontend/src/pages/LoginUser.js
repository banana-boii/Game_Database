import React, { useState } from 'react';
import { loginUser } from '../api';

function LoginUser() {
  const [loginData, setLoginData] = useState({ username: '', password: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = await loginUser(loginData);
      alert(`Login successful! Token: ${token}`);
    } catch (error) {
      console.error('Error logging in:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Login User</h2>
      <input name="username" placeholder="Username" onChange={handleChange} required />
      <input name="password" placeholder="Password" type="password" onChange={handleChange} required />
      <button type="submit">Login</button>
    </form>
  );
}

export default LoginUser;
