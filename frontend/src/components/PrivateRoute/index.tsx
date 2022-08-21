import { Navigate } from 'react-router-dom';
import { isAuthenticated } from '../../util/requests';

type Props = {
    children: React.ReactComponentElement<any>;
};
const PrivateRoute = ({ children }: Props) => {
    return isAuthenticated() ? children : <Navigate to='/admin/auth/login' />;
};
export default PrivateRoute;