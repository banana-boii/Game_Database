import React, { useState } from 'react';
import axios from 'axios';
import GameItem from './GameItem';

const GameSearch = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSearch = async () => {
        if (!searchTerm.trim()) {
            return; // Don't search if the search term is empty
        }
        
        setIsLoading(true);
        setError(null);
        
        try {
            const response = await axios.get(`http://localhost:8080/api/games/search?title=${encodeURIComponent(searchTerm)}`);
            console.log('Search results:', response.data);
            
            // Check if response.data is an array
            if (Array.isArray(response.data)) {
                setSearchResults(response.data);
            } else if (response.data.content && Array.isArray(response.data.content)) {
                // If the API returns paginated data like in GameList
                setSearchResults(response.data.content);
            } else {
                console.error('Unexpected response format:', response.data);
                setError('Received unexpected data format from server');
                setSearchResults([]);
            }
        } catch (error) {
            console.error('Error fetching search results:', error);
            setError('Failed to fetch search results. Please try again.');
            setSearchResults([]);
        } finally {
            setIsLoading(false);
        }
    };

    // Handle Enter key press
    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

    return (
        <div>
            <h2>Search Games</h2>
            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    onKeyPress={handleKeyPress}
                    placeholder="Search for a game..."
                    style={{ padding: '8px', marginRight: '10px', width: '300px' }}
                />
                <button 
                    onClick={handleSearch}
                    disabled={isLoading}
                    style={{ padding: '8px 16px' }}
                >
                    {isLoading ? 'Searching...' : 'Search'}
                </button>
            </div>
            
            {error && <p style={{ color: 'red' }}>{error}</p>}
            
            {searchResults.length === 0 && !isLoading && !error ? (
                <p>No games found. Try a different search term.</p>
            ) : (
                <ul>
                    {searchResults.map((result) => {
                        console.log('Result item:', result);
                        
                        // Transform the result to match what GameItem expects
                        // The search API returns flat objects, but GameItem expects a nested structure
                        const gameItemProps = {
                            game: {
                                gameId: result.gameId,
                                name: result.title, // GameItem uses 'name' not 'title'
                                releaseDate: result.releaseDate || 'Unknown',
                                price: result.price || 0,
                                shortDescription: result.description || ''
                            },
                            headerImage: result.headerImage,
                            images: result.images || [],
                            firstVideo: result.firstVideo || null
                        };
                        
                        return (
                            <GameItem
                                key={result.gameId}
                                {...gameItemProps}
                            />
                        );
                    })}
                </ul>
            )}
        </div>
    );
};

export default GameSearch;
