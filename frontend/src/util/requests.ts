import axios from 'axios';
import qs from 'qs';

const api = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL ?? 'http://localhost:8080',
});
export default api;

const CLIENT_ID = import.meta.env.VITE_CLIENT_ID ?? 'my_app';
const CLIENT_SECRET = import.meta.env.VITE_CLIENT_SECRET ?? 'my_app_secret';
const TOKEN_KEY = 'authData';

type LoginResponse = {
  access_token: string;
  token_type: string;
  expires_in: number;
  scope: string;
  userLastName: string;
  userFirstName: string;
  userId: number;
}

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

export const saveAuthData = (obj : LoginResponse) => {
  localStorage.setItem(TOKEN_KEY, JSON.stringify(obj));
}

export const getAuthData = () => {
  const str = localStorage.getItem(TOKEN_KEY) ?? '{}';
  return JSON.parse(str) as LoginResponse;
}
