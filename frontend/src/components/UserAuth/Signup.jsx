import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../../context/AuthContext';
import { signupUser } from '../../services/Api';
import './signup.css';

const Signup = () => {
    const [formData, setFormData] = useState({
        fullName: '',
        username: '',
        password: '',
        email: '',
        phoneNumber: '',
        role: '',
    });

    const [errors, setErrors] = useState({});
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    const validateForm = () => {
        const newErrors = {};

        if (formData.fullName.length < 2) {
            newErrors.fullName = 'Full name must be at least 2 characters long';
        }

        if (formData.username.length < 5) {
            newErrors.username = 'Username must be at least 5 characters long';
        }

        const passwordRegex = /^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]+$/;
        if (formData.password.length < 8 || !passwordRegex.test(formData.password)) {
            newErrors.password = 'Password must be at least 8 characters long and contain at least one special character and one digit';
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(formData.email)) {
            newErrors.email = 'Invalid email format';
        }

        const phoneRegex = /^\d{10}$/;
        if (!phoneRegex.test(formData.phoneNumber)) {
            newErrors.phoneNumber = 'Phone number must be 10 digits';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (validateForm()) {
            try {
                const response = await signupUser(formData);
                console.log(response);
                login(response.token, response.user);
                navigate('/');
            } catch (error) {
                console.error('Signup failed:', error);
            }
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    return (
        <div className="signup-page">
            {/* Left Side Content */}
            <div className="signup-info">
                <h2>📚 Welcome to Book Haven!</h2>
                <p>Discover millions of books across all genres. Sign up now and start exploring the world of literature.</p>
                <img src="/assets/bookstore-illustration.svg" alt="Bookstore Illustration" className="info-image" />
            </div>

            {/* Signup Form */}
            <div className="signup-container">
                <h1 className="signup-title">Create Your Account</h1>
                <form className="signup-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className='form-label'>Full Name</label>
                        <input type="text" name="fullName" value={formData.fullName} onChange={handleChange} className="form-input" placeholder="Full Name" required />
                        {errors.fullName && <p className="error-message">{errors.fullName}</p>}
                    </div>

                    <div className="form-group">
                        <label className='form-label'>Username</label>
                        <input type="text" name="username" value={formData.username} onChange={handleChange} className="form-input" placeholder="Username" required />
                        {errors.username && <p className="error-message">{errors.username}</p>}
                    </div>

                    <div className="form-group">
                        <label className='form-label'>Password</label>
                        <input type="password" name="password" value={formData.password} onChange={handleChange} className="form-input" placeholder="Password" required />
                        {errors.password && <p className="error-message">{errors.password}</p>}
                    </div>

                    <div className="form-group">
                        <label className='form-label'>Email</label>
                        <input type="email" name="email" value={formData.email} onChange={handleChange} className="form-input" placeholder="Email" required />
                        {errors.email && <p className="error-message">{errors.email}</p>}
                    </div>

                    <div className="form-group">
                        <label className='form-label'>Phone Number</label>
                        <input type="text" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} className="form-input" placeholder="Phone Number" required />
                        {errors.phoneNumber && <p className="error-message">{errors.phoneNumber}</p>}
                    </div>

                    <div className="form-group">
                        <label className='form-label'>Role</label>
                        <select name="role" className="form-select" onChange={handleChange} required>
                            <option value="">Select Role</option>
                            <option value="USER">USER</option>
                            <option value="ADMIN">ADMIN</option>
                        </select>
                    </div>

                    <button type="submit" className="signup-button">Sign Up</button>

                    <div className="form-footer">
                        <p className="form-footer-text">
                            Already have an account?{" "}
                            <a href="/login" className="login-link">
                                Log In
                            </a>
                        </p>
                    </div>
                </form>
            </div>

            {/* Right Side Content */}
            <div className="signup-info">
                <h2>📖 Your Library, Anytime, Anywhere</h2>
                <p>Save books to your wishlist, track your reading, and connect with fellow book lovers!</p>
                <img src="/assets/reading-illustration.svg" alt="Reading Illustration" className="info-image" />
            </div>
        </div>
    );
};

export default Signup;
