import React, { useEffect, useState } from 'react';
import api from '../api/axiosConfig';

const Dashboard = () => {
    const [rides, setRides] = useState([]);

    useEffect(() => {
        const fetchRides = async () => {
            try {
                // This calls your @GetMapping("/all") in RideController
                const response = await api.get('/ride/all');
                setRides(response.data);
            } catch (error) {
                console.error("Error fetching rides", error);
            }
        };
        fetchRides();
    }, []);

    return (
        <div style={{ padding: '20px' }}>
            <h1>Available Rides</h1>
            <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>Pickup</th>
                        <th>Destination</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {rides.map(ride => (
                        <tr key={ride.id}>
                            <td>{ride.pickupLocation}</td>
                            <td>{ride.destination}</td>
                            <td>${ride.fare}</td>
                            <td><button>Book Now</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Dashboard;