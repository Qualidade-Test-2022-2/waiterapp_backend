import axios from 'axios'

export const api = axios.create({
  baseURL: `http://${process.env.REACT_APP_HOST}:8080/api`,
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  },
});
