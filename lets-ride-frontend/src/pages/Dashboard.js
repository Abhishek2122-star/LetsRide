import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';

const Dashboard = () => {
    const [rides, setRides] = useState([]);

    useEffect(() => {
        const fetchRides = async () => {
            try {
                // Ensure your Backend RideController has @GetMapping("/all")
                const response = await api.get('/ride/all');
                setRides(response.data);
            } catch (error) {
                console.error("Error fetching rides", error);
            }
        };
        fetchRides();
    }, []);

    return (
        <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h1>Available Rides</h1>
            <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                <thead style={{ backgroundColor: '#f4f4f4' }}>
                    <tr>
                        <th>ID</th>
                        <th>Pickup Location</th>
                        <th>Drop Location</th>
                        <th>Ride Type</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {rides.length > 0 ? (
                        rides.map(ride => (
                            <tr key={ride.id}>
                                <td>{ride.id}</td>
                                <td>{ride.pickupLocation}</td>
                                <td>{ride.dropLocation}</td> {/* Match: drop_location */}
                                <td>{ride.rideType}</td>     {/* Match: ride_type */}
                                <td>
                                    <span style={{
                                        color: ride.status === 'REQUESTED' ? 'orange' : 'green',
                                        fontWeight: 'bold'
                                    }}>
                                        {ride.status}
                                    </span>
                                </td>
                                <td>
                                    <button style={{ cursor: 'pointer' }}>Details</button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="6" style={{ textAlign: 'center' }}>No rides found. Add some to MySQL!</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
};

export default Dashboard;