import './styles.css';
import 'bootstrap/js/src/collapse.js';
import { Link } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import { getTokenData, isAuthenticated, removeAuthData, TokenData } from '../../util/requests';
import { useContext, useEffect } from 'react';
import history from '../../util/history';
import { AuthContext } from '../../AuthContext';

const Navbar = () => {

    const { authContextData, setAuthContextData } = useContext(AuthContext);

    useEffect(() => {
        if (isAuthenticated()) {
            setAuthContextData({ authenticated: true, tokenData: getTokenData() });
        } else {
            setAuthContextData({ authenticated: false });
        }
    }, [setAuthContextData]);

    const handleLogoutClick = (event: React.MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault();
        removeAuthData();
        setAuthContextData({ authenticated: false });
        history.push('/');
    }

    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-primary main-nav">
            <div className='container-fluid'>
                <Link to={'/'} className='nav-logo-text'>
                    <h4>DS Catalog</h4>
                </Link>
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#dscatalog-navbar"
                    aria-controls="dscatalog-navbar"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className='collapse navbar-collapse' id='dscatalog-navbar'>
                    <ul className='navbar-nav offset-md-2 main-menu'>
                        <li>
                            <NavLink to='/'>HOME</NavLink>
                        </li>
                        <li>
                            <NavLink to={'/products'}>CATÁLOGO</NavLink>
                        </li>
                        <li>
                            <NavLink to={'/admin'}>ADMIN</NavLink>
                        </li>
                    </ul>
                </div>
                <div className='nav-login-logout'>
                    {
                        authContextData.authenticated ? (
                            <div>
                                <span className='nav-username'>{authContextData.tokenData?.user_name}</span>
                                <a href="#logout" onClick={handleLogoutClick}>LOGOUT</a>
                            </div>
                        ) : (
                            <Link to={'/admin/auth'}>LOGIN</Link>
                        )
                    }
                </div>
            </div>
        </nav>
    );
}

export default Navbar