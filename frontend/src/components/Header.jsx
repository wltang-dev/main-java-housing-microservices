// src/components/Header.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Header() {
    const navigate = useNavigate();
    const token = localStorage.getItem('token');
    const isLoggedIn = !!token;

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <header style={{
            background: 'linear-gradient(to right, #004d40, #009688)',
            padding: '20px 40px',
            color: 'white',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            boxShadow: '0 2px 8px rgba(0,0,0,0.15)'
        }}>
            <div style={{ fontSize: '28px', fontWeight: 'bold' }}>
                🏡 RealEstateX —Housing Launch Rush Portal
            </div>
            {isLoggedIn && (
                <div style={{ fontSize: '16px' }}>
                    <Link to="/houses" style={{ color: 'white', marginRight: '20px', textDecoration: 'none' }}>House List</Link>
                    <span
                        onClick={handleLogout}
                        style={{ color: 'white', cursor: 'pointer' }}
                    >
                        Logout
                    </span>
                </div>
            )}
        </header>
    );
}

export default Header;
