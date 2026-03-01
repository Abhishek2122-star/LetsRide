import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import './Dashboard.css';

const Dashboard = () => {
    const [rides, setRides] = useState([]);

    useEffect(() => {
        const fetchRides = async () => {
            try {
                const response = await api.get('/ride/all');
                setRides(response.data);
            } catch (error) {
                console.error("Error fetching rides", error);
            }
        };
        fetchRides();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        window.location.href = "/login";
    };

    return (
        <div className="dashboard-layout">
            {/* 1. Sidebar */}
            <aside className="sidebar">
                <h2>LetsRide</h2>
                <div className="nav-item active">Dashboard</div>
                <div className="nav-item">My Bookings</div>
                <div className="nav-item">Profile</div>
                <div className="nav-item" onClick={handleLogout} style={{marginTop: 'auto', color: '#f87171'}}>Logout</div>
            </aside>

            {/* 2. Main Content */}
            <main className="main-content">
                <header className="header">
                    <h1>Available Rides</h1>
                    <div className="user-badge">👋 Welcome, User</div>
                </header>

                <div className="table-card">
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #f1f5f9', textAlign: 'left', color: '#64748b' }}>
                                <th style={{ padding: '12px' }}>RIDE ID</th>
                                <th>PICKUP</th>
                                <th>Drop</th>
                                <th>TYPE</th>
                                <th>STATUS</th>
                                <th>ACTION</th>
                            </tr>
                        </thead>
                        <tbody>
                            {rides.length > 0 ? rides.map(ride => (
                                <tr key={ride.id} style={{ borderBottom: '1px solid #f8fafc' }}>
                                    <td style={{ padding: '16px' }}>#{ride.id}</td>
                                    <td>{ride.pickupLocation}</td>
                                    <td>{ride.dropLocation}</td>
                                    <td><strong>{ride.rideType}</strong></td>
                                    <td>
                                        <span className={`status-pill ${ride.status === 'REQUESTED' ? 'status-requested' : 'status-completed'}`}>
                                            {ride.status}
                                        </span>
                                    </td>
                                    <td>
                                        <button style={{ background: '#3b82f6', color: 'white', border: 'none', padding: '6px 12px', borderRadius: '6px', cursor: 'pointer' }}>
                                            View
                                        </button>
                                    </td>
                                </tr>
                            )) : (
                                <tr><td colSpan="6" style={{ textAlign: 'center', padding: '2rem' }}>No active rides found.</td></tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    );
};

export default Dashboard;