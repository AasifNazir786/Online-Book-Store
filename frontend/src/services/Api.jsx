import axios from "axios";

const BASE_URL = 'http://localhost:8080/api';
const token = localStorage.getItem('token') || '';

const api = axios.create({
    baseURL: BASE_URL,
});

export const loginUser = async (credentials) => {
    console.log('Credentials Data '+credentials.username, credentials.password)
    try {
        const response = await api.post('/auth/login', credentials);
        console.log('Login response:', response.data);
        return response.data;
    } catch (error) {
        console.error('Login error:', error.response?.data || error.message);
        throw error;
    }
};

export const signupUser = async (userData) => {
    try {
        const response = await api.post('/auth/register', userData);
        console.log('Signup response:', response.data); // Debugging
        return response.data;
    } catch (error) {
        console.error('Signup error:', error.response?.data || error.message);
        throw error;
    }
};


export const getBooks = async () => {
    const response = await api.get('/books', {
        headers: {
            Authorization: token ? `Bearer ${token}` : ''
        }
    })

    return response.data
}