import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [houses, setHouses] = useState([]);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [mode, setMode] = useState('login'); // "login" or "register"

    // 登录后获取房源列表
    useEffect(() => {
        if (isLoggedIn) {
            axios.get('http://localhost:30090/api/client/house/list', { withCredentials: true })
                .then(response => {
                    console.log('房源接口返回：', response.data);

                    // 防止返回的不是数组时报错
                    if (Array.isArray(response.data)) {
                        setHouses(response.data);
                    } else {
                        console.error('房源接口返回的不是数组：', response.data);
                        setHouses([]);
                    }
                })
                .catch(error => {
                    console.error('获取房源失败：', error);
                });
        }
    }, [isLoggedIn]);

    // 登录或注册处理函数
    const handleAuth = async () => {
        try {
            const url = mode === 'login'
                ? 'http://localhost:30090/api/auth/login'
                : 'http://localhost:30090/api/auth/register';

            const res = await axios.post(url, { username, password }, { withCredentials: true });

            console.log('认证成功：', res.data);
            alert('认证成功');
            setIsLoggedIn(true);
        } catch (err) {
            console.error('认证失败: ', err);
            alert('用户名或密码错误');
        }
    };

    // 未登录：显示登录/注册界面
    if (!isLoggedIn) {
        return (
            <div>
                <h2>{mode === 'login' ? '登录' : '注册'}</h2>
                <input
                    type="text"
                    placeholder="用户名"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
                <br />
                <input
                    type="password"
                    placeholder="密码"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                <br />
                <button onClick={handleAuth}>
                    {mode === 'login' ? '登录' : '注册'}
                </button>
                <p
                    onClick={() => setMode(mode === 'login' ? 'register' : 'login')}
                    style={{ cursor: 'pointer', color: 'blue', marginTop: '10px' }}
                >
                    {mode === 'login' ? '没有账号？点击注册' : '已有账号？点击登录'}
                </p>
            </div>
        );
    }

    // 已登录：显示房源列表
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
