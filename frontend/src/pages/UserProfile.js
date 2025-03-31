import React, { useState } from 'react';
import { getUserProfile } from '../api';

function UserProfile() {
  const [userId, setUserId] = useState('');
  const [profile, setProfile] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await getUserProfile(userId);
      setProfile(data);
    } catch (error) {
      console.error('Error fetching user profile:', error);
    }
  };

  return (
    <div>
      <h2>User Profile</h2>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="User ID"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
        />
        <button type="submit">Get Profile</button>
      </form>
      {profile && (
        <div>
          <p>Name: {profile.name}</p>
          <p>Email: {profile.email}</p>
          <p>Age: {profile.age}</p>
          <p>Username: {profile.username}</p>
        </div>
      )}
    </div>
  );
}

export default UserProfile;
