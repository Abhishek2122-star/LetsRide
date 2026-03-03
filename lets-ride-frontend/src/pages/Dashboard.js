import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import BookingModal from './BookingModal'; // Import your separate component
import './Dashboard.css';

const Dashboard = () => {
    const [rides, setRides] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    // Fetch rides from Backend
    const fetchRides = async () => {
        try {
            const response = await api.get('/ride/all');
            setRides(response.data);
        } catch (error) {
            console.error("Error fetching rides", error);
        }
    };

    useEffect(() => {
        fetchRides();
    }, []);

    // Logic to save the ride through the API
    const handleSaveRide = async (rideData) => {
        try {
            const response = await api.post('/ride/book', rideData);
            // Optimization: Update table locally so we don't have to refresh the whole page
            setRides([...rides, response.data]);
            setIsModalOpen(false); // Close the popup
            alert("Ride booked successfully!");
        } catch (error) {
            console.error("Booking failed", error);
            alert("Error: Could not save the ride.");
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        window.location.href = "/login";
    };

    return (
        <div className="dashboard-layout">
            {/* Sidebar */}
            <aside className="sidebar">
                <h2>LetsRide</h2>
                <div className="nav-item active">Dashboard</div>
                <div className="nav-item">My Bookings</div>
                <div className="nav-item">Profile</div>
                <div className="nav-item" onClick={handleLogout} style={{marginTop: 'auto', color: '#f87171'}}>
                    Logout
                </div>
            </aside>

            {/* Main Content */}
            <main className="main-content">
                <header className="header">
                    <h1>Available Rides</h1>
                    <div style={{ display: 'flex', gap: '15px' }}>
                        <button className="btn-add-ride" onClick={() => setIsModalOpen(true)}>
                            + Book New Ride
                        </button>
                        <div className="user-badge">👋 Welcome, User</div>
                    </div>
                </header>

                <div className="table-card">
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #f1f5f9', textAlign: 'left', color: '#64748b' }}>
                                <th style={{ padding: '12px' }}>RIDE ID</th>
                                <th>PICKUP</th>
                                <th>DROP</th>
                                <th>TYPE</th>
                                <th>STATUS</th>
                                <th>ACTION</th>
                            </tr>
                        </thead>
                        <tbody>
                            {rides.length > 0 ? (
                                rides.map(ride => (
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
                                            <button className="view-btn">View</button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="6" style={{ textAlign: 'center', padding: '2rem' }}>
                                        No active rides found.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </main>

            {/* Booking Modal Component */}
            <BookingModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onSave={handleSaveRide}
            />
        </div>
    );
};

export default Dashboard;