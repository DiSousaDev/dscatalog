import { ReactComponent as ArrowIcon } from '../../assets/images/arrow.svg'
import ProductPrice from '../../components/ProductPrice';

import './styles.css';

const ProductDetails = () => {
    return (
        <div className='product-detail-container'>
            <div className='product-detail-card base-card'>
                <div className='goback-container'>
                    <ArrowIcon />
                    <h2>VOLTAR</h2>
                </div>
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
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. In dolor architecto consequuntur, repudiandae, explicabo cupiditate, voluptas atque nesciunt debitis molestiae fugiat quisquam iusto facere eaque quis. A, nisi! Temporibus possimus nihil nam ipsam incidunt alias voluptatibus qui tempore ea nemo neque fuga libero eos dolorum, maxime saepe dolorem ex natus.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProductDetails;