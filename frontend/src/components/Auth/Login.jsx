import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../context/AuthContext";
import { loginUser } from "../../services/Api";
import "./styles/style.css";

const Login = () => {

    const [loginData, setLoginData] = useState({
        username: '',
        password: '',
    })
    const [errors, setErrors] = useState({});
    const {login} = useContext(AuthContext);
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
        e.preventDefault()
        if(validateForm){
            try {
                console.log("Enters login to fetch token//////////")
                const response = await loginUser(loginData);
                console.log('Login Data '+loginData.username, loginData.password)
                console.log('Response Data '+response.token, response.user.username, response.user.password)
                login(response.token, response.user);
                navigate('/');
            } catch (error) {
                console.error('login failed', error)
            }
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setLoginData({ ...loginData, [name]: value });
    };

    return (
        <div className="login-container">
            <h1 className="login-title">Login</h1>
            <form className="login-form" onSubmit={handleSubmit}>
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
                        Don’t have an account?{" "}
                        <a href="/signup" className="signup-link">click here</a>
                    </p>
                    <a href="#" className="forgot-password">Forgot Password?</a>
                </div>
            </form>
        </div>
    );
};

export default Login;