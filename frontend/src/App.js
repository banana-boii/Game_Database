import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import GameList from './pages/GameList'; // This will now serve as the Home page
import GameDetails from './pages/GameDetails';
import RegisterUser from './pages/RegisterUser';
import LoginUser from './pages/LoginUser';
import AddReview from './pages/AddReview';
import AddToLibrary from './pages/AddToLibrary';
import GetLibrary from './pages/GetLibrary';
import UserProfile from './pages/UserProfile';
import './styles/App.css';


function App() {
  const token = localStorage.getItem('jwtToken'); // Retrieve the token from local storage

  return (
    <Router>
      <div className="App">
        <header>
          <h1>Game Database</h1>
          <nav>
            <ul>
              <li><Link to="/">Home</Link></li> {/* Renamed Game List to Home */}
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
            <Route path="/" element={<GameList />} /> {/* Updated route for Home */}
            <Route path="/games/:id" element={<GameDetails />} />
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
