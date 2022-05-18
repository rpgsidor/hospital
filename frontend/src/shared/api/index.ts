import axios from 'axios';

export const configureAxios = () => {
  axios.defaults.baseURL = 'http://localhost:8080';

  axios.interceptors.request.use((config) => {
    const accessToken = localStorage.getItem('access-token');

    if (accessToken) {
      config.headers = {
        ...config.headers,
        Authorization: `Bearer ${accessToken}`,
      };
    }

    return config;
  });
};
