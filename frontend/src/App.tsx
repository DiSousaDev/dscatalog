import './assets/styles/custom.scss';
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import Catalog from './pages/Catalog';
import Admin from './pages/Admin';

const App = () => (
  <BrowserRouter>
    <Navbar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/products" element={<Catalog />} />
      <Route path="/admin" element={<Admin />} />
    </Routes>
  </BrowserRouter>
)

export default App