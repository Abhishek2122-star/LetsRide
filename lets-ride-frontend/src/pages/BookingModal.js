//import React, { useState } from 'react';
//const BookingModal = ({ isOpen, onClose, onSave }) => {
//    const [newRide, setNewRide] = useState({
//        pickupLocation: '',
//        dropLocation: '',
//        rideType: 'SEDAN'
//    });
//
//    if (!isOpen) return null;
//
//    const handleSubmit = (e) => {
//        e.preventDefault();
//        onSave(newRide); // Pass data back to Dashboard
//        // Reset local state for next time
//        setNewRide({ pickupLocation: '', dropLocation: '', rideType: 'SEDAN' });
//    };
//
//    return (
//        <div className="modal-overlay">
//            <div className="modal-content">
//                <h2>Create New Booking</h2>
//                <form onSubmit={handleSubmit}>
//                    <div className="form-group">
//                        <label>Pickup Location</label>
//                        <input
//                            type="text"
//                            placeholder="Enter Pickup Point"
//                            value={newRide.pickupLocation}
//                            onChange={(e) => setNewRide({...newRide, pickupLocation: e.target.value})}
//                            required
//                        />
//                    </div>
//
//                    <div className="form-group">
//                        <label>Drop Location</label>
//                        <input
//                            type="text"
//                            placeholder="Enter Drop Location"
//                            value={newRide.dropLocation}
//                            onChange={(e) => setNewRide({...newRide, dropLocation: e.target.value})}
//                            required
//                        />
//                    </div>
//
//                    <div className="form-group">
//                        <label>Car Type</label>
//                        <select
//                            value={newRide.rideType}
//                            onChange={(e) => setNewRide({...newRide, rideType: e.target.value})}
//                        >
//                            <option value="SEDAN">Sedan</option>
//                            <option value="SUV">SUV</option>
//                            <option value="PREMIUM">Premium</option>
//                        </select>
//                    </div>
//
//                    <div className="modal-btns">
//                        <button type="button" className="btn-secondary" onClick={onClose}>
//                            Cancel
//                        </button>
//                        <button type="submit" className="btn-primary">
//                            Book Now
//                        </button>
//                    </div>
//                </form>
//            </div>
//        </div>
//    );
//};
//
//export default BookingModal;