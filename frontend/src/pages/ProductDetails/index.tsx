import { Link } from 'react-router-dom';
import { ReactComponent as ArrowIcon } from '../../assets/images/arrow.svg'
import ProductPrice from '../../components/ProductPrice';

import './styles.css';

const ProductDetails = () => {
    return (
        <div className='product-detail-container'>
            <div className='product-detail-card base-card'>
                <Link to={'/products'}>
                    <div className='goback-container'>
                        <ArrowIcon />
                        <h2>VOLTAR</h2>
                    </div>
                </Link>
                <div className='row'>
                    <div className='col-xl-6'>
                        <div className='img-container'>
                            <img src='https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg' alt='tenis' />
                        </div>
                        <div className='name-price-container'>
                            <h1>Nome do Produto</h1>
                            <ProductPrice price={2190.0} />
                        </div>
                    </div>
                    <div className='col-xl-6'>
                        <div className='description-container'>
                            <h2>Descrição do produto</h2>
                            <p>Lorem ipsum dolor sit amet, consectetur.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProductDetails;