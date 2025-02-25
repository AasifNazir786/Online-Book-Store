
import PropTypes from 'prop-types';
import Body from '../Body/Body.jsx';
import Navbar from '../Navbar/Navbar.jsx';
import "./dashboard.css";

const Dashboard = ({ user, cartCount, wishlistCount }) => {
    return (
        <div className="dashboard">
            {/* Navbar */}
            <Navbar user={user} cartCount={cartCount} wishlistCount={wishlistCount} />

            {/* Body */}
            <Body />
        </div>
    );
};
Dashboard.propTypes = {
    user: PropTypes.object.isRequired,
    cartCount: PropTypes.number.isRequired,
    wishlistCount: PropTypes.number.isRequired,
};
export default Dashboard;