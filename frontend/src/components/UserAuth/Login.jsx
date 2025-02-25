import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../context/AuthContext";
import { loginUser } from "../../services/Api";
import "./login.css";

const Login = () => {
    const [loginData, setLoginData] = useState({
        username: '',
        password: '',
    });

    const [errors, setErrors] = useState({});
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    const validateForm = () => {
        const newErrors = {};

        if (loginData.username.length < 5) {
            newErrors.username = 'Username must be at least 5 characters long';
        }

        const passwordRegex = /^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]+$/;
        if (loginData.password.length < 8 || !passwordRegex.test(loginData.password)) {
            newErrors.password =
                'Password must be at least 8 characters long and contain at least one special character and one digit';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        try {
            const response = await loginUser(loginData);
            login(response.token, response.user);
            navigate('/');
        } catch (error) {
            setErrors({ login: 'Invalid username or password' });
            alert(error)
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setLoginData({ ...loginData, [name]: value });
    };

    return (
        <div className="main-container">
            {/* Left Side Content */}
            <div className="bookstore-info">
                <h2>📚 Unlock the World of Books</h2>
                <p>Find your next favorite book, track your reading journey, and explore endless literary adventures.</p>
                <img src="/assets/book-shelf.svg" alt="Bookshelf" className="info-image" />
            </div>

            {/* Login Form */}
            <div className="login-container">
                <h1 className="login-title">Login</h1>
                <form className="login-form" onSubmit={handleSubmit}>
                    {errors.login && <p className="error-message">{errors.login}</p>}
                    
                    <div className="form-group">
                        <label className="form-label">Username</label>
                        <input
                            type="text"
                            name="username"
                            value={loginData.username}
                            onChange={handleChange}
                            className="form-input"
                            required
                        />
                        {errors.username && <p className="error-message">{errors.username}</p>}
                    </div>

                    <div className="form-group">
                        <label className="form-label">Password</label>
                        <input
                            type="password"
                            name="password"
                            value={loginData.password}
                            onChange={handleChange}
                            className="form-input"
                            required
                        />
                        {errors.password && <p className="error-message">{errors.password}</p>}
                    </div>

                    <div className="form-group">
                        <button type="submit" className="login-button">Login</button>
                    </div>

                    <div className="form-footer">
                        <p>
                            <b>Don’t have an account?{" "}</b>
                            <a href="/signup" className="signup-link">Sign up here</a>
                        </p>
                        <a href="#" className="forgot-password">Forgot Password?</a>
                    </div>
                </form>
            </div>

            {/* Right Side Content */}
            <div className="bookstore-info">
                <h2>📖 Your Personal Library</h2>
                <p>Save books, track progress, and dive into the world of literature with our online bookstore.</p>
                <img src="/assets/reading-corner.svg" alt="Reading Corner" className="info-image" />
            </div>
        </div>
    );
};

export default Login;
