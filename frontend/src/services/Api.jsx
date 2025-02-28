import axios from "axios";

const BASE_URL = 'http://localhost:8080/api';
// const token = localStorage.getItem('token') || '';

const api = axios.create({
    baseURL: BASE_URL,
});

export const loginUser = async (credentials) => {
    try {
        const response = await api.post('/auth/login', credentials);
        return response.data;
    } catch (error) {
        console.error('Login error:', error.response?.data || error.message);
        throw error;
    }
};

export const logoutUser = async () => {
    try {
        const response = await api.post("/auth/logout");
        return response.data;
    } catch (error) {
        console.error('logout error:', error.response?.data || error.message)
    }
}

export const signupUser = async (userData) => {
    try {
        const response = await api.post('/auth/register', userData);
        return response.data;
    } catch (error) {
        console.error('Signup error:', error.response?.data || error.message);
        throw error;
    }
};


export const getBooks = async (page, size) => {
    const response = await api.get(`/books/all?page=${page}&size=${size}`);
    return response.data
};