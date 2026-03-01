import React, { useState } from 'react';
import api from '../api/axiosConfig';
import './Auth.css'; // Make sure to import the CSS

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/auth/login', { email, password });
            localStorage.setItem('token', response.data.token);
            window.location.href = "/dashboard";
        } catch (error) {
            console.error("Login failed", error);
            alert("Oops! Invalid credentials. Check your email/password.");
        }
    };

    return (
        <div className="auth-wrapper">
            <div className="auth-card">
                <h2>Welcome Back</h2>
                <p>Login to start your journey with LetsRide</p>

                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Email Address</label>
                        <input
                            type="email"
                            placeholder="name@example.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" className="btn-auth">Sign In</button>
                </form>

                <div className="auth-footer">
                    Don't have an account? <a href="/signup">Create account</a>
                </div>
            </div>
        </div>
    );
};

export default Login;