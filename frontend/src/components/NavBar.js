import React from 'react';
import { Link } from 'react-router-dom';

function NavBar({ username, genres }) {
  return (
    <nav style={{ display: 'flex', justifyContent: 'space-between', padding: '10px', backgroundColor: '#1b2838', color: '#fff' }}>
      {/* Left Section */}
      <div style={{ display: 'flex', gap: '20px' }}>
        <Link to="/games" style={{ color: '#fff', textDecoration: 'none' }}>Store</Link>
        <div>
          <span style={{ cursor: 'pointer' }}>Library</span>
          <div style={{ position: 'absolute', backgroundColor: '#2a475e', padding: '10px', display: 'none' }}>
            <Link to="/get-favorites" style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>Favorites</Link>
            <Link to="/get-library" style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>Library</Link>
          </div>
        </div>
        <div>
          <span style={{ cursor: 'pointer' }}>{username}</span>
          <div style={{ position: 'absolute', backgroundColor: '#2a475e', padding: '10px', display: 'none' }}>
            <Link to="/user-profile" style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>Profile</Link>
          </div>
        </div>
      </div>

      {/* Middle Section */}
      <div style={{ display: 'flex', gap: '20px' }}>
        <div>
          <span style={{ cursor: 'pointer' }}>New & Noteworthy</span>
          <div style={{ position: 'absolute', backgroundColor: '#2a475e', padding: '10px', display: 'none' }}>
            <Link to="/top-sellers" style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>Top Sellers</Link>
            <Link to="/most-played" style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>Most Played</Link>
            <Link to="/new-releases" style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>New Releases</Link>
          </div>
        </div>
        <div>
          <span style={{ cursor: 'pointer' }}>Categories</span>
          <div style={{ position: 'absolute', backgroundColor: '#2a475e', padding: '10px', display: 'none' }}>
            {genres.map((genre) => (
              <Link key={genre.genre_id} to={`/categories/${genre.name}`} style={{ color: '#fff', textDecoration: 'none', display: 'block' }}>
                {genre.name}
              </Link>
            ))}
          </div>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;
