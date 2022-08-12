import './styles.css';
import { NavLink } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className='admin-nav-container'>
            <ul>
                <li>
                    <NavLink to='/admin/products' className='admin-nav-item'><p>Produtos</p></NavLink>
                </li>
                <li>
                    <NavLink to='/admin/categories' className='admin-nav-item'><p>Categorias</p></NavLink>
                </li>
                <li>
                    <NavLink to='/admin/admin' className='admin-nav-item'><p>Admin</p></NavLink>
                </li>
            </ul>
        </nav>
    );
}

export default Navbar