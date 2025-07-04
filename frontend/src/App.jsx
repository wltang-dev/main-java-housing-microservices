import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [houses, setHouses] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:9000/api/client/house/list')
            .then(response => {
                setHouses(response.data);
            })
            .catch(error => {
                console.error('请求失败：', error);
            });
    }, []);

    return (
        <div>
            <h1>房源列表</h1>
            <ul>
                {houses.map((house, index) => (
                    <li key={index}>
                        房源 ID: {house.id} - 名称: {house.name}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default App;
