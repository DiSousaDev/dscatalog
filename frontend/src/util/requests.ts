import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';
import history from './history';

const CLIENT_ID = import.meta.env.VITE_CLIENT_ID ?? 'my_app';
const CLIENT_SECRET = import.meta.env.VITE_CLIENT_SECRET ?? 'my_app_secret';
const TOKEN_KEY = 'authData';

const api = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL ?? 'http://localhost:8080',
});
// Add a request interceptor
api.interceptors.request.use(function (config) {
  return config;
}, function (error) {
  return Promise.reject(error);
});

// Add a response interceptor
api.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  if(error.response.status === 401 || error.response.status === 403) {
    history.push('/admin/auth');
  }
  return Promise.reject(error);
});
export default api;

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

export const requestBackend = (config : AxiosRequestConfig) => {

  const headers = config.withCredentials ? {
    ...config.headers,
    Authorization: 'Bearer ' + getAuthData().access_token,
  } : config.headers;

  return api( {...config, headers} );
}

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

export const saveAuthData = (obj : LoginResponse) => {
  localStorage.setItem(TOKEN_KEY, JSON.stringify(obj));
}

export const getAuthData = () => {
  const str = localStorage.getItem(TOKEN_KEY) ?? '{}';
  return JSON.parse(str) as LoginResponse;
}