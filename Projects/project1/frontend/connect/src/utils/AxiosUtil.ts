import axios from "axios";

const URL = process.env.REACT_APP_DB_API_URL;

const axiosUtil = axios.create({
  baseURL: URL,
  headers: {
    'Content-Type': 'application/json',
  }
});

// Add JWT token from localStorage to each request's Authorization header
axiosUtil.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('token');
  if (token) {
    config.headers.set('Authorization', `Bearer ${token}`);
  } else {
    config.headers.delete('Authorization');
  }
  return config;
}, (error) => Promise.reject(error));

export default axiosUtil;