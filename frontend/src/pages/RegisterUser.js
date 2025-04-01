import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './RegisterUser.css'; // Make sure to create this CSS file in the same directory

function RegisterUser() {
  const [formData, setFormData] = useState({
    name: '',
    phone: '',
    email: '',
    age: '',
    username: '',
    password: '',
  });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Registration failed: ${errorText}`);
      }

      navigate('/login'); // Redirect to login page after successful registration
    } catch (err) {
      console.error('Registration error:', err);
      setError(err.message);
    }
  };

  return (
    <div className="form-container">
      <form className="form" onSubmit={handleSubmit}>
        <p className="title">Register</p>
        <p className="message">Create an account to access the game library.</p>
        
        <label>
          <input
            className="input"
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            placeholder=""
          />
          <span>Name</span>
        </label>
        
        <label>
          <input
            className="input"
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            placeholder=""
          />
          <span>Phone</span>
        </label>
        
        <label>
          <input
            className="input"
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder=""
          />
          <span>Email</span>
        </label>
        
        <label>
          <input
            className="input"
            type="number"
            name="age"
            value={formData.age}
            onChange={handleChange}
            required
            placeholder=""
          />
          <span>Age</span>
        </label>
        
        <label>
          <input
            className="input"
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
            placeholder=""
          />
          <span>Username</span>
        </label>
        
        <label>
          <input
            className="input"
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            placeholder=""
          />
          <span>Password</span>
        </label>
        
        <button className="submit" type="submit">Register</button>
        {error && <p className="error-message">{error}</p>}
        <p className="signin">
          Already have an account? <a href="/login">Login</a>
        </p>
      </form>
    </div>
  );
}

export default RegisterUser;