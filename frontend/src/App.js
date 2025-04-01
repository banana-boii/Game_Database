import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import GameList from './pages/GameList';
import GameDetails from './pages/GameDetails';
import RegisterUser from './pages/RegisterUser';
import LoginUser from './pages/LoginUser';
import AddReview from './pages/AddReview';
import AddToLibrary from './pages/AddToLibrary';
import GetLibrary from './pages/GetLibrary';
import UserProfile from './pages/UserProfile';
import GameSearch from './pages/GameSearch';
import './styles/App.css'; // Ensure this import is present

function App() {
  const token = localStorage.getItem('jwtToken');

  return (
    <Router>
      <div className="App">
        <header>
          <h1 style={{ fontSize: '24px' }}>Game Database</h1> {/* Increased font size */}
          <nav>
            <ul style={{ fontSize: '18px' }}> {/* Increased font size */}
              <li><Link to="/">Home</Link></li>
              <li><Link to="/search">Search</Link></li>
              <li><Link to="/register">Register</Link></li>
              <li><Link to="/login">Login</Link></li>
              <li><Link to="/add-review">Add Review</Link></li>
              <li><Link to="/add-to-library">Add to Library</Link></li>
              <li><Link to="/get-library">Get Library</Link></li>
              <li><Link to="/user-profile">User Profile</Link></li>
            </ul>
          </nav>
        </header>
        <main>
          <Routes>
            <Route path="/" element={<GameList />} />
            <Route path="/games/:id" element={<GameDetails />} />
            <Route path="/search" element={<GameSearch />} />
            <Route path="/register" element={<RegisterUser />} />
            <Route path="/login" element={<LoginUser />} />
            <Route path="/add-review" element={<AddReview />} />
            <Route path="/add-to-library" element={<AddToLibrary />} />
            <Route path="/get-library" element={<GetLibrary token={token} />} />
            <Route path="/user-profile" element={<UserProfile />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;