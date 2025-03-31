import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import GameList from './pages/GameList';
import GameDetails from './pages/GameDetails';
import RegisterUser from './pages/RegisterUser';
import LoginUser from './pages/LoginUser';
import AddReview from './pages/AddReview';
import AddFavorite from './pages/AddFavorite';
import AddToLibrary from './pages/AddToLibrary';
import RemoveFavorite from './pages/RemoveFavorite';
import GetFavorites from './pages/GetFavorites';
import GetLibrary from './pages/GetLibrary';
import UserProfile from './pages/UserProfile';
import Logout from './pages/Logout';
import ProtectedRoute from './components/ProtectedRoute';
import './styles/App.css';

function App() {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const token = localStorage.getItem('token'); // Check if the user is logged in

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem('token'); // Clear the token
    window.location.href = '/login'; // Redirect to login page
  };

  return (
    <Router>
      <div className="App">
        <header>
          <h1>Game Database</h1>
          <nav>
            <ul className="nav-links">
              <li><Link to="/">Home</Link></li>
              <li><Link to="/games">Game List</Link></li>
              {!token && <li><Link to="/register">Register</Link></li>}
              {!token && <li><Link to="/login">Login</Link></li>}
              {token && (
                <li className="profile-menu">
                  <div onClick={toggleDropdown} className="profile-icon">
                    <img
                      src="https://via.placeholder.com/40" // Replace with a profile icon URL
                      alt="Profile"
                      className="profile-image"
                    />
                  </div>
                  {isDropdownOpen && (
                    <ul className="dropdown-menu">
                      <li><Link to="/user-profile">Profile</Link></li>
                      <li><Link to="/get-favorites">Favorites</Link></li>
                      <li><Link to="/get-library">Library</Link></li>
                      <li onClick={handleLogout}>Logout</li>
                    </ul>
                  )}
                </li>
              )}
            </ul>
          </nav>
        </header>
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/games" element={<GameList />} />
            <Route path="/games/:id" element={<GameDetails />} />
            <Route path="/register" element={<RegisterUser />} />
            <Route path="/login" element={<LoginUser />} />
            <Route
              path="/add-review"
              element={
                <ProtectedRoute>
                  <AddReview />
                </ProtectedRoute>
              }
            />
            <Route
              path="/add-favorite"
              element={
                <ProtectedRoute>
                  <AddFavorite />
                </ProtectedRoute>
              }
            />
            <Route
              path="/add-to-library"
              element={
                <ProtectedRoute>
                  <AddToLibrary />
                </ProtectedRoute>
              }
            />
            <Route
              path="/remove-favorite"
              element={
                <ProtectedRoute>
                  <RemoveFavorite />
                </ProtectedRoute>
              }
            />
            <Route
              path="/get-favorites"
              element={
                <ProtectedRoute>
                  <GetFavorites />
                </ProtectedRoute>
              }
            />
            <Route
              path="/get-library"
              element={
                <ProtectedRoute>
                  <GetLibrary />
                </ProtectedRoute>
              }
            />
            <Route
              path="/user-profile"
              element={
                <ProtectedRoute>
                  <UserProfile />
                </ProtectedRoute>
              }
            />
            <Route path="/logout" element={<Logout />} />
          </Routes>
        </main>
        {/* Footer removed */}
      </div>
    </Router>
  );
}

export default App;
