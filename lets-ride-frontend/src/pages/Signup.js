import React, { useState } from 'react';
import api from '../api/axiosConfig';
import './Auth.css';

const Signup = () => {
    const [formData, setFormData] = useState({ name: '', email: '', password: '' });

    const handleSignup = async (e) => {
        e.preventDefault();
        try {
            await api.post('/auth/register', formData);
            alert("Registration Successful! Please login.");
            window.location.href = "/login";
        } catch (error) {
            alert(error.response?.data || "Registration failed");
        }
    };

    return (
        <div className="auth-wrapper">
            <div className="auth-card">
                <h2>Join LetsRide</h2>
                <p>Create an account to book rides easily</p>

                <form onSubmit={handleSignup}>
                    <div className="form-group">
                        <label>Full Name</label>
                        <input
                            type="text"
                            placeholder="John Doe"
                            onChange={(e) => setFormData({...formData, name: e.target.value})}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Email Address</label>
                        <input
                            type="email"
                            placeholder="name@example.com"
                            onChange={(e) => setFormData({...formData, email: e.target.value})}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            placeholder="Min 8 characters"
                            onChange={(e) => setFormData({...formData, password: e.target.value})}
                            required
                        />
                    </div>

                    <button type="submit" className="btn-auth">Create Account</button>
                </form>

                <div className="auth-footer">
                    Already have an account? <a href="/login">Sign In</a>
                </div>
            </div>
        </div>
    );
};

export default Signup;