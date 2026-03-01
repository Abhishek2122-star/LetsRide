// In your React Component (e.g., BookRide.js)
const rideData = {
    pickupLocation: pickup,   // Must match Java field name
    dropLocation: dropLocation, // Must match Java field name
    rideType: type           // Must match Java field name
};

await axios.post("http://localhost:8080/ride/book", rideData, {
    headers: { Authorization: `Bearer ${token}` }
});