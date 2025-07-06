// src/components/HouseCard.jsx
import React from 'react';

function HouseCard({ house, onSeckill }) {
    const isAvailable = house.available;

    return (
        <div
            style={{
                border: '1px solid #ccc',
                borderRadius: '6px',
                padding: '10px',
                marginBottom: '10px'
            }}
        >
            <p><strong>House ID:</strong> {house.id}</p>
            <p><strong>Building:</strong> {house.buildingNumber}</p>
            <p><strong>Room:</strong> {house.floorRoomNumber}</p>
            <p><strong>Layout:</strong> {house.houseType}</p>
            <p><strong>Price:</strong> {house.price} CNY</p>

            {/* ✅ 关键部分：按钮区域 */}
            {isAvailable ? (
                <button
                    onClick={() => onSeckill(house.id)} // 调用父组件传来的函数
                    style={{
                        padding: '8px 16px',
                        backgroundColor: 'green',
                        color: 'white',
                        border: 'none',
                        borderRadius: '8px',
                        cursor: 'pointer',
                        marginTop: '10px'
                    }}
                >
                    Available - Click to Buy
                </button>
            ) : (
                <span
                    style={{
                        padding: '8px 16px',
                        backgroundColor: 'gray',
                        color: 'white',
                        borderRadius: '8px',
                        marginTop: '10px',
                        display: 'inline-block'
                    }}
                >
                    Sold
                </span>
            )}
        </div>
    );
}

export default HouseCard;
