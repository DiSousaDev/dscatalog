import axios from 'axios';
import qs from 'qs';

const api = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL ?? 'http://localhost:8080',
});

export default api;

const CLIENT_ID = import.meta.env.VITE_CLIENT_ID ?? 'my_app';
const CLIENT_SECRET = import.meta.env.VITE_CLIENT_SECRET ?? 'my_app_secret';

type LoginData = {
  username: string;
  password: string;
};

export const requestBackendLogin = (loginData: LoginData) => {
  const headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
    Authorization: 'Basic ' + btoa(CLIENT_ID + ':' + CLIENT_SECRET),
  };

  const data = qs.stringify({
    ...loginData,
    grant_type: 'password'
  });

  return api.post('/oauth/token', data, { headers });

};
