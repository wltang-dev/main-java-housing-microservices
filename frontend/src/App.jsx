import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [houses, setHouses] = useState([]);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [mode, setMode] = useState('login'); // "login" or "register"

    // Fetch house list after login
    useEffect(() => {
        if (isLoggedIn) {
            const token = localStorage.getItem('token');  // Get token from local storage

            axios.get('http://localhost:30090/api/client/house/list', {
                headers: {
                    Authorization: 'Bearer ' + token
                },
                withCredentials: true
            })
                .then(response => {
                    console.log('House API response:', response.data);
                    if (Array.isArray(response.data)) {
                        setHouses(response.data);
                    } else {
                        console.error('House API did not return an array:', response.data);
                        setHouses([]);
                    }
                })
                .catch(error => {
                    console.error('Failed to fetch houses:', error);
                });
        }
    }, [isLoggedIn]);

    // Handle login or registration
    const handleAuth = async () => {
        try {
            const url = mode === 'login'
                ? 'http://localhost:30090/api/auth/login'
                : 'http://localhost:30090/api/auth/register';

            const res = await axios.post(url, { username, password }, { withCredentials: true });

            console.log('Authentication success:', res.data);
            alert('Authentication successful');
            const token = res.data;
            if (token) {
                localStorage.setItem('token', token);
                setIsLoggedIn(true);
            } else {
                alert('Login failed: No token returned');
            }
        } catch (err) {
            console.error('Authentication failed:', err);
            alert('Invalid username or password');
        }
    };

    // If not logged in: show login/register UI
    if (!isLoggedIn) {
        return (
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                backgroundColor: '#f5f5f5'
            }}>
                <div style={{
                    backgroundColor: 'white',
                    padding: '40px',
                    borderRadius: '12px',
                    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
                    minWidth: '300px'
                }}>
                    <h2 style={{textAlign: 'center', marginBottom: '20px'}}>
                        {mode === 'login' ? 'Login' : 'Register'}
                    </h2>
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            marginBottom: '12px',
                            borderRadius: '6px',
                            border: '1px solid #ccc'
                        }}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            marginBottom: '20px',
                            borderRadius: '6px',
                            border: '1px solid #ccc'
                        }}
                    />
                    <button
                        onClick={handleAuth}
                        style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '6px',
                            border: 'none',
                            backgroundColor: '#4CAF50',
                            color: 'white',
                            fontWeight: 'bold',
                            cursor: 'pointer'
                        }}
                    >
                        {mode === 'login' ? 'Login' : 'Register'}
                    </button>
                    <p
                        onClick={() => setMode(mode === 'login' ? 'register' : 'login')}
                        style={{
                            textAlign: 'center',
                            color: '#007bff',
                            marginTop: '15px',
                            cursor: 'pointer'
                        }}
                    >
                        {mode === 'login'
                            ? 'No account? Click to register'
                            : 'Already have an account? Click to login'}
                    </p>
                </div>
            </div>
        );
    }

    // If logged in: show house list
    return (
        <div>
            <h1>House List</h1>
            <ul style={{listStyle: 'none', padding: 0}}>
                {houses.map((house, index) => (
                    <li key={index} style={{
                        border: '1px solid #ccc',
                        marginBottom: '10px',
                        padding: '10px',
                        borderRadius: '5px'
                    }}>
                        <div>
                            <strong>House ID:</strong> {house.id}<br/>
                            <strong>Building:</strong> {house.buildingNumber}
                            <strong>Room:</strong> {house.floorRoomNumber}<br/>
                            <strong>Layout:</strong> {house.houseType} <strong>Price:</strong> {house.price} CNY<br/>
                        </div>
                        <div style={{
                            padding: '2px 8px',
                            borderRadius: '12px',
                            fontSize: '14px',
                            color: 'white',
                            backgroundColor: house.available ? 'green' : 'gray',
                            display: 'inline-block'
                        }}>
                            {house.available ? 'Available' : 'Sold'}
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default App;
