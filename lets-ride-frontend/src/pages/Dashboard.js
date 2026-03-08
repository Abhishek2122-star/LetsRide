import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import BookingModal from './BookingModal';
import './Dashboard.css';

const Dashboard = () => {

    const [rides, setRides] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    // ================= FETCH USER RIDES =================
    const fetchRides = async () => {
        try {
            const response = await api.get('/ride/my-rides');
            setRides(response.data);
        } catch (error) {
            console.error("Error fetching rides", error);
        }
    };

    useEffect(() => {
        fetchRides();
    }, []);

    // ================= BOOK RIDE =================
    const handleSaveRide = async (rideData) => {
        try {

            const response = await api.post('/ride/book', {
                pickupLocation: rideData.pickupLocation,
                dropLocation: rideData.dropLocation,
                rideType: rideData.rideType
            });

            setRides([...rides, response.data]);
            setIsModalOpen(false);

            alert("Ride booked successfully!");

        } catch (error) {
            console.error("Booking failed", error);
            alert("Error: Could not save the ride.");
        }
    };

    // ================= LOGOUT =================
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

                <div
                    className="nav-item"
                    onClick={handleLogout}
                    style={{ marginTop: 'auto', color: '#f87171' }}
                >
                    Logout
                </div>
            </aside>


            {/* Main Content */}
            <main className="main-content">

                <header className="header">
                    <h1>My Rides</h1>

                    <div style={{ display: 'flex', gap: '15px' }}>
                        <button
                            className="btn-add-ride"
                            onClick={() => setIsModalOpen(true)}
                        >
                            + Book New Ride
                        </button>

                        <div className="user-badge">
                            👋 Welcome
                        </div>
                    </div>
                </header>


                <div className="table-card">

                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>

                        <thead>
                            <tr
                                style={{
                                    borderBottom: '2px solid #f1f5f9',
                                    textAlign: 'left',
                                    color: '#64748b'
                                }}
                            >
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

                                rides.map((ride) => (

                                    <tr
                                        key={ride.id}
                                        style={{ borderBottom: '1px solid #f8fafc' }}
                                    >

                                        <td style={{ padding: '16px' }}>
                                            #{ride.id}
                                        </td>

                                        <td>{ride.pickupLocation}</td>

                                        <td>{ride.dropLocation}</td>

                                        <td>
                                            <strong>{ride.rideType}</strong>
                                        </td>

                                        <td>
                                            <span
                                                className={`status-pill ${
                                                    ride.status === 'REQUESTED'
                                                        ? 'status-requested'
                                                        : 'status-completed'
                                                }`}
                                            >
                                                {ride.status}
                                            </span>
                                        </td>

                                        <td>
                                            <button className="view-btn">
                                                View
                                            </button>
                                        </td>

                                    </tr>

                                ))

                            ) : (

                                <tr>
                                    <td
                                        colSpan="6"
                                        style={{
                                            textAlign: 'center',
                                            padding: '2rem'
                                        }}
                                    >
                                        No rides booked yet.
                                    </td>
                                </tr>

                            )}

                        </tbody>

                    </table>

                </div>

            </main>


            {/* BOOKING MODAL */}
            <BookingModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onSave={handleSaveRide}
            />

        </div>
    );
};

export default Dashboard;