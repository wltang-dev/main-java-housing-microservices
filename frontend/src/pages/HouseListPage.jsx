// src/pages/HouseListPage.jsx
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import HouseCard from '../components/HouseCard';

function HouseListPage() {
    const [houses, setHouses] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const navigate = useNavigate();
    const BASE_URL = import.meta.env.VITE_API_BASE;

    useEffect(() => {
        const token = localStorage.getItem('token');
        axios.get(`${BASE_URL}/api/client/house/list`, {
            headers: { Authorization: 'Bearer ' + token },
            withCredentials: true
        })
            .then(res => setHouses(res.data || []))
            .catch(err => console.error('Failed to fetch houses:', err));
    }, []);

    const handleSeckill = async (houseId) => {
        const token = localStorage.getItem('token');
        try {
            const res = await axios.post(
                `${BASE_URL}/api/client/house/seckill/${houseId}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    withCredentials: true,
                }
            );

            if (res.data.status === 'success') {
                navigate(res.data.paymentPage);  // ✅ 跳转到支付页
            } else {
                alert(res.data.message || '抢房失败');
            }
        } catch (err) {
            console.error('抢房请求失败:', err);
            alert('请求出错，请稍后再试');
        }
    };

    const totalPages = Math.ceil(houses.length / itemsPerPage);
    const currentItems = houses.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

    return (
        <div style={{ padding: '20px' }}>
            <h1>House List</h1>
            {currentItems.map((house, i) => (
                <HouseCard key={i} house={house} onSeckill={handleSeckill} />
            ))}

            <div style={{ marginTop: '20px', textAlign: 'center' }}>
                {Array.from({ length: totalPages }, (_, i) => (
                    <button
                        key={i}
                        onClick={() => setCurrentPage(i + 1)}
                        style={{
                            margin: '0 5px',
                            padding: '5px 10px',
                            backgroundColor: currentPage === i + 1 ? '#007bff' : '#eee',
                            color: currentPage === i + 1 ? '#fff' : '#000',
                            border: 'none',
                            borderRadius: '5px',
                            cursor: 'pointer'
                        }}
                    >
                        {i + 1}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default HouseListPage;
