import React from 'react';
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
import './styles/App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <header>
          <h1>Game Database</h1>
          <nav>
            <ul>
              <li><Link to="/">Home</Link></li>
              <li><Link to="/games">Game List</Link></li>
              <li><Link to="/register">Register</Link></li>
              <li><Link to="/login">Login</Link></li>
              <li><Link to="/add-review">Add Review</Link></li>
              <li><Link to="/add-favorite">Add Favorite</Link></li>
              <li><Link to="/add-to-library">Add to Library</Link></li>
              <li><Link to="/remove-favorite">Remove Favorite</Link></li>
              <li><Link to="/get-favorites">Get Favorites</Link></li>
              <li><Link to="/get-library">Get Library</Link></li>
              <li><Link to="/user-profile">User Profile</Link></li>
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
            <Route path="/add-review" element={<AddReview />} />
            <Route path="/add-favorite" element={<AddFavorite />} />
            <Route path="/add-to-library" element={<AddToLibrary />} />
            <Route path="/remove-favorite" element={<RemoveFavorite />} />
            <Route path="/get-favorites" element={<GetFavorites />} />
            <Route path="/get-library" element={<GetLibrary />} />
            <Route path="/user-profile" element={<UserProfile />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
