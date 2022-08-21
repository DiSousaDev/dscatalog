import { unstable_HistoryRouter as HistoryRouter, Navigate, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Admin from './pages/Admin';
import CategoryCrud from './pages/Admin/CategoryCrud';
import ProductCrud from './pages/Admin/ProductCrud';
import Catalog from './pages/Catalog';
import Home from './pages/Home';
import ProductDetails from './pages/ProductDetails';

import './assets/styles/custom.scss';
import './App.css';
import Auth from './pages/Admin/Auth';
import LoginCard from './pages/Admin/Auth/LoginCard';
import SignupCard from './pages/Admin/Auth/SignupCard';
import RecoverCard from './pages/Admin/Auth/RecoverCard';
import UserCrud from './pages/Admin/UserCrud';
import history from './util/history';

const App = () => (
  <HistoryRouter history={history}>
    <Navbar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="products" element={<Catalog />} />
      <Route path="products/:productId" element={<ProductDetails />} />
      <Route path="admin" element={<Navigate replace to="/admin/products" />} />
      <Route path="admin" element={<Admin />}>
        <Route path="products" element={<ProductCrud />} />
        <Route path="categories" element={<CategoryCrud />} />
        <Route path="users" element={<UserCrud />} />
      </Route>
      <Route path="admin/auth" element={<Navigate replace to="/admin/auth/login" />} />
      <Route path="admin/auth" element={<Auth />}>
        <Route path="login" element={<LoginCard />} />
        <Route path="signup" element={<SignupCard />} />
        <Route path="recover" element={<RecoverCard />} />
      </Route>
    </Routes>
  </HistoryRouter>
)

export default App