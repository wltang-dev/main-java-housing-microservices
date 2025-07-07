// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function LoginPage() {
    const [mode, setMode] = useState('login');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const BASE_URL = import.meta.env.VITE_API_BASE;



    const handleAuth = async () => {
        try {
            const url = mode === 'login'
                ? `${BASE_URL}/api/auth/login`
                : `${BASE_URL}/api/auth/register`;
            const res = await axios.post(url, { username, password }, { withCredentials: true });
            const { status, token, message } = res.data;

            if (status === 'success' && token) {
                localStorage.setItem('token', token);
                navigate('/houses');
            } else {
                alert(message || 'login failed. Please verify your credentials.');
            }
        } catch (err) {
            console.error('Authentication failed:', err);
            alert('Invalid username or password');
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
            <div style={{ backgroundColor: '#fff', padding: '30px', borderRadius: '12px', boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>
                <h2 style={{ textAlign: 'center' }}>{mode === 'login' ? 'Login' : 'Register'}</h2>
                <input type="text" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} style={{ width: '100%', padding: '10px', margin: '10px 0' }} />
                <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} style={{ width: '100%', padding: '10px', marginBottom: '15px' }} />
                <button onClick={handleAuth} style={{ width: '100%', padding: '10px', backgroundColor: '#4CAF50', color: 'white', border: 'none', borderRadius: '6px' }}>
                    {mode === 'login' ? 'Login' : 'Register'}
                </button>
                <p onClick={() => setMode(mode === 'login' ? 'register' : 'login')} style={{ textAlign: 'center', color: '#007bff', marginTop: '15px', cursor: 'pointer' }}>
                    {mode === 'login' ? 'No account? Click to register' : 'Already have an account? Click to login'}
                </p>
            </div>
        </div>
    );
}

export default LoginPage;
