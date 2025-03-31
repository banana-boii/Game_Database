import React, { useState } from 'react';
import { registerUser } from '../api';
import '../styles/FormStyles.css'; // Import the shared CSS file

function RegisterUser() {
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    age: '',
    username: '',
    passwordHash: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await registerUser(userData);
      alert(response);
    } catch (error) {
      console.error('Error registering user:', error);
    }
  };

  return (
    <div>
      <div className="page-background"></div> {/* Blurred background */}
      <div className="form-container">
        <h2 className="title">Register</h2>
        <form className="form" onSubmit={handleSubmit}>
          <div className="input-group">
            <label htmlFor="name">Name</label>
            <input id="name" name="name" type="text" onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label htmlFor="email">Email</label>
            <input id="email" name="email" type="email" onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label htmlFor="age">Age</label>
            <input id="age" name="age" type="number" onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label htmlFor="username">Username</label>
            <input id="username" name="username" type="text" onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label htmlFor="passwordHash">Password</label>
            <input id="passwordHash" name="passwordHash" type="password" onChange={handleChange} required />
          </div>
          <button className="sign" type="submit">Register</button>
        </form>
      </div>
    </div>
  );
}

export default RegisterUser;
