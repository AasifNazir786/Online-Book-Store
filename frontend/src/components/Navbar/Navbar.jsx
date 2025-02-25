import PropTypes from "prop-types";
import { useContext, useEffect, useState } from "react";
import { FaBars, FaHeart, FaMoon, FaShoppingCart, FaSun, FaTimes } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../context/AuthContext.jsx";
import { logoutUser } from "../../services/Api.jsx";
import "./Navbar.css";

const Navbar = ({ user, cartCount, wishlistCount }) => {
    const [menuOpen, setMenuOpen] = useState(false);
    const [darkMode, setDarkMode] = useState(false);
    const { logout } = useContext(AuthContext);
    const navigate = useNavigate();

    // Toggle Dark Mode
    const toggleDarkMode = () => {
        setDarkMode(!darkMode);
        localStorage.setItem("darkMode", !darkMode);
    };

    // Apply Dark Mode on Initial Load
    useEffect(() => {
        const savedDarkMode = localStorage.getItem("darkMode") === "true";
        setDarkMode(savedDarkMode);
    }, []);

    // Handle Logout
    const handleLogout = async () => {
        try {
            await logoutUser();
            logout();
            navigate("/login");
        } catch (error) {
            console.error("Logout failed", error);
        }
    };

    const categories = ["Fiction", "Non-Fiction", "Mystery", "Fantasy", "Science", "Romance"];

    return (
        <nav className={`navbar ${darkMode ? "dark-mode" : "light-mode"}`}>
            <div className="navbar-container">
                {/* Logo */}
                <a href="/" className="navbar-logo">
                    📚 Book Haven
                </a>
                
                {/* Desktop Navigation */}
                <div className="navbar-links">
                    <a href="/books">Books</a>
                    <a href="/bestsellers">Bestsellers</a>
                    <a href="/recent-books">Recent Books</a>
                    <a href="/awarded-books">Awarded Books</a>

                    {/* Categories Dropdown */}
                    <div className="dropdown">
                        <button className="dropbtn">Categories ▼</button>
                        <div className="dropdown-content">
                            {categories.map((category) => (
                                <a key={category} href={`/category/${category}`}>
                                    {category}
                                </a>
                            ))}
                        </div>
                    </div>

                    {/* Wishlist & Cart */}
                    <a href="/wishlist" className="icon-link">
                        <FaHeart size={24} className="icon" />
                        {wishlistCount > 0 && <span className="badge">{wishlistCount}</span>}
                    </a>

                    <a href="/cart" className="icon-link">
                        <FaShoppingCart size={24} className="icon" />
                        {cartCount > 0 && <span className="badge">{cartCount}</span>}
                    </a>

                    {/* Dark Mode Toggle */}
                    <button className="dark-mode-toggle" onClick={toggleDarkMode}>
                        {darkMode ? <FaSun size={24} className="icon" /> : <FaMoon size={24} className="icon" />}
                    </button>

                    {/* My Account Dropdown */}
                    <div className="dropdown">
                        <button className="dropbtn">My Account ▼</button>
                        <div className="dropdown-content">
                            {user ? (
                                <>
                                    <a href="/profile">Profile</a>
                                    <a href="/orders">Orders</a>
                                    <a href="/wishlist">Wishlist</a>
                                    <a href="/settings">Personal Settings</a>
                                    <button onClick={handleLogout} className="logout-btn">
                                        Logout
                                    </button>
                                </>
                            ) : (
                                <>
                                    <a href="/login">Login</a>
                                    <div className="signup-btn">
                                        <p>New to BookHaven?</p>
                                        <a href="/signup">Signup</a>
                                    </div>
                                    
                                </>
                            )}
                        </div>
                    </div>

                    {/* Admin Panel (Only for Admins) */}
                    {user?.role === "ADMIN" && (
                        <div className="dropdown">
                            <button className="dropbtn">Admin Panel ▼</button>
                            <div className="dropdown-content">
                                <a href="/admin/books">Manage Books</a>
                                <a href="/admin/orders">Orders Dashboard</a>
                                <a href="/admin/users">Users Management</a>
                            </div>
                        </div>
                    )}
                </div>

                {/* Mobile Menu Button */}
                <button className="mobile-menu-button" onClick={() => setMenuOpen(!menuOpen)}>
                    {menuOpen ? <FaTimes size={24} className="icon" /> : <FaBars size={24} className="icon" />}
                </button>
            </div>

            {/* Mobile Navigation */}
            {menuOpen && (
                <div className="mobile-menu">
                    <a href="/books">Books</a>
                    <a href="/bestsellers">Bestsellers</a>
                    <a href="/recent-books">Recent Books</a>
                    <a href="/awarded-books">Awarded Books</a>
                    <a href="/categories">Categories</a>
                    <a href="/wishlist">Wishlist</a>
                    <a href="/cart">Cart</a>

                    {/* Dark Mode Toggle */}
                    <button className="dark-mode-toggle" onClick={toggleDarkMode}>
                        {darkMode ? <FaSun size={24} className="icon" /> : <FaMoon size={24} className="icon" />}
                    </button>

                    {/* My Account Dropdown */}
                    {user ? (
                        <>
                            <a href="/profile">Profile</a>
                            <a href="/orders">Orders</a>
                            <a href="/wishlist">Wishlist</a>
                            <a href="/settings">Personal Settings</a>
                            <button onClick={handleLogout} className="logout-btn">
                                Logout
                            </button>
                        </>
                    ) : (
                        <>
                            <a href="/login">Login</a>
                            <a href="/signup">New to Book Haven? Signup</a>
                        </>
                    )}

                    {/* Admin Panel (Only for Admins) */}
                    {user?.role === "ADMIN" && (
                        <>
                            <a href="/admin/books">Manage Books</a>
                            <a href="/admin/orders">Orders Dashboard</a>
                            <a href="/admin/users">Users Management</a>
                        </>
                    )}
                </div>
            )}
        </nav>
    );
};

Navbar.propTypes = {
    user: PropTypes.object, // Can be null if unauthenticated
    cartCount: PropTypes.number.isRequired,
    wishlistCount: PropTypes.number.isRequired,
};

export default Navbar;