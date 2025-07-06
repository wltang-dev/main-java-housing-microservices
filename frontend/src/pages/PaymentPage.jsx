import { useSearchParams, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

function PaymentPage() {
    const [params] = useSearchParams();
    const houseId = params.get('houseId');
    const navigate = useNavigate();

    const handleFakePay = () => {
        alert('💰 支付成功！订单已生成。');
        navigate('/houses');
    };

    useEffect(() => {
        if (!houseId) {
            alert('无效访问，未指定房源');
            navigate('/houses');
        }
    }, [houseId, navigate]);

    return (
        <div style={{ padding: '50px', textAlign: 'center' }}>
            <h2>🧾 House Payment</h2>
            <p>You're about to pay for house ID: <strong>{houseId}</strong></p>
            <p>Price: 730000 CNY</p>
            <button
                onClick={handleFakePay}
                style={{
                    padding: '10px 20px',
                    backgroundColor: '#4CAF50',
                    color: 'white',
                    fontSize: '16px',
                    border: 'none',
                    borderRadius: '6px',
                    cursor: 'pointer',
                    marginTop: '20px'
                }}
            >
                Confirm Payment (Simulated)
            </button>
        </div>
    );
}

export default PaymentPage;
