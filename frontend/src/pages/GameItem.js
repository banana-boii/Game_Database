import React from 'react';

const GameItem = ({ game, headerImage, images, firstVideo }) => {
    return (
        <li>
            <h3>{game.name}</h3>
            <div style={{ display: 'flex', overflowX: 'auto', gap: '10px', paddingBottom: '10px' }}>
                {headerImage && (
                    <img
                        src={headerImage}
                        alt={`${game.name} header`}
                        style={{
                            height: '300px',
                            width: `${300 * (16 / 9)}px`,
                            objectFit: 'cover',
                            borderRadius: '8px',
                        }}
                    />
                )}
                {firstVideo && (
                    <video controls style={{ height: '300px', width: `${300 * (16 / 9)}px`, borderRadius: '8px' }}>
                        <source src={firstVideo} type="video/mp4" />
                        Your browser does not support the video tag.
                    </video>
                )}
                {images && images.map((image, index) => (
                    <img
                        key={index}
                        src={image}
                        alt={`${game.name} screenshot ${index + 1}`}
                        style={{
                            height: '300px',
                            width: `${300 * (16 / 9)}px`,
                            objectFit: 'cover',
                            borderRadius: '8px',
                        }}
                    />
                ))}
            </div>
            <p>Release Date: {game.releaseDate}</p>
            <p>Price: ${game.price}</p>
            <p>Short Description: {game.shortDescription}</p>
        </li>
    );
};

export default GameItem;