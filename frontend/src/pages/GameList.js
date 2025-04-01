import React, { useEffect, useState } from 'react';
import NavBar from '../components/NavBar';
import FeaturedGames from '../components/FeaturedGames';
import PopularGames from '../components/PopularGames';
import { fetchGames } from '../api';

function GameList() {
  const [games, setGames] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [genres, setGenres] = useState([]);
  const [featuredGames, setFeaturedGames] = useState([]);
  const [popularGames, setPopularGames] = useState([]);

  useEffect(() => {
    const loadGames = async () => {
      try {
        const data = await fetchGames(page);
        console.log('Fetched games:', data); // Debugging
        setGames(data.content); // `content` contains the list of games
        setTotalPages(data.totalPages); // `totalPages` contains the total number of pages
      } catch (error) {
        console.error('Error fetching games:', error);
        alert('Failed to load games. Please try again.');
      }
    };

    const loadGenres = async () => {
      try {
        const response = await fetch('/api/genres');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setGenres(data);
      } catch (error) {
        console.error('Error fetching genres:', error);
      }
    };

    const loadFeaturedGames = async () => {
      try {
        const response = await fetch('/api/games/featured');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setFeaturedGames(data);
      } catch (error) {
        console.error('Error fetching featured games:', error);
      }
    };

    const loadPopularGames = async () => {
      try {
        const response = await fetch('/api/games/popular');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setPopularGames(data);
      } catch (error) {
        console.error('Error fetching popular games:', error);
      }
    };

    loadGames();
    loadGenres();
    loadFeaturedGames();
    loadPopularGames();
  }, [page]);

  const handleNextPage = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
    }
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  return (
    <div>
      <NavBar username="Banana_Boi" genres={genres} />
      <FeaturedGames games={featuredGames} />
      <PopularGames games={popularGames} />
      <h2>Game List</h2>
      <ul>
        {games.map(({ game, headerImage, images, firstVideo }) => (
          <li key={game.gameId}>
            <h3>{game.name}</h3>
            {/* Horizontal Scrollbar for Header Image, Video, and Screenshots */}
            <div style={{ display: 'flex', overflowX: 'auto', gap: '10px', paddingBottom: '10px' }}>
              {/* Header Image */}
              {headerImage && (
                <img
                  src={headerImage}
                  alt={`${game.name} header`}
                  style={{
                    height: '300px', // Fixed height
                    width: `${300 * (16 / 9)}px`, // Maintain 16:9 aspect ratio
                    objectFit: 'cover',
                    borderRadius: '8px',
                  }}
                />
              )}
              {/* Video */}
              {firstVideo && (
                <video controls style={{ height: '300px', width: `${300 * (16 / 9)}px`, borderRadius: '8px' }}>
                  <source src={firstVideo} type="video/mp4" />
                  Your browser does not support the video tag.
                </video>
              )}
              {/* Screenshots */}
              {images && images.map((image, index) => (
                <img
                  key={index}
                  src={image}
                  alt={`${game.name} screenshot ${index + 1}`}
                  style={{
                    height: '300px', // Fixed height
                    width: `${300 * (16 / 9)}px`, // Maintain 16:9 aspect ratio
                    objectFit: 'cover',
                    borderRadius: '8px',
                  }}
                />
              ))}
            </div>
            {/* Game Details */}
            <p>Release Date: {game.releaseDate}</p>
            <p>Price: ${game.price}</p>
            <p>Short Description: {game.shortDescription}</p>
          </li>
        ))}
      </ul>
      <div>
        <button onClick={handlePreviousPage} disabled={page === 0}>
          Previous
        </button>
        <button onClick={handleNextPage} disabled={page === totalPages - 1}>
          Next
        </button>
      </div>
    </div>
  );
}

export default GameList;
