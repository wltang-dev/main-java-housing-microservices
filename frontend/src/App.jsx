import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import HouseListPage from './pages/HouseListPage';
import Layout from './components/Layout';

function App() {
    const isLoggedIn = !!localStorage.getItem('token');

    return (
        <Router>
            <Layout>
                <Routes>
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/houses" element={isLoggedIn ? <HouseListPage /> : <Navigate to="/login" />} />
                    <Route path="*" element={<Navigate to="/login" />} />
                </Routes>
            </Layout>
        </Router>
    );
}

export default App;
