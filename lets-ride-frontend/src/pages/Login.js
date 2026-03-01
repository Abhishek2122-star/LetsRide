import React, { useState } from 'react';
import api from '../api/axiosConfig'; // This uses the "bridge" we just built

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/auth/login', { email, password });
            // Save the token so axiosConfig can use it later
            localStorage.setItem('token', response.data.token);
            alert("Login Successful!");
            window.location.href = "/dashboard"; // 👈 This moves the user to the dashboard
            console.log("Token:", response.data.token);
        } catch (error) {
            console.error("Login failed", error);
            alert("Invalid email or password");
        }
    };

    return (
        <div style={{ marginTop: '50px', textAlign: 'center' }}>
            <h2>Login to LetsRide</h2>
            <form onSubmit={handleLogin}>
                <input type="email" placeholder="Email" value={email}
                    onChange={(e) => setEmail(e.target.value)} required /><br/><br/>
                <input type="password" placeholder="Password" value={password}
                    onChange={(e) => setPassword(e.target.value)} required /><br/><br/>
                <button type="submit">Sign In</button>
            </form>
        </div>
    );
};

export default Login;