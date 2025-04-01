import React, { useEffect, useState } from 'react';
import { fetchGames } from '../api';
import GameItem from './GameItem'; // Import from the same directory

function GameList() {
    const [games, setGames] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        const loadGames = async () => {
            try {
                const data = await fetchGames(page);
                console.log('Fetched games:', data);
                setGames(data.content);
                setTotalPages(data.totalPages);
            } catch (error) {
                console.error('Error fetching games:', error);
                alert('Failed to load games. Please try again.');
            }
        };

        loadGames();
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
            <h2>Game List</h2>
            <ul>
                {games.map(({ game, headerImage, images, firstVideo }) => (
                    <GameItem
                        key={game.gameId}
                        game={game}
                        headerImage={headerImage}
                        images={images}
                        firstVideo={firstVideo}
                    />
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
