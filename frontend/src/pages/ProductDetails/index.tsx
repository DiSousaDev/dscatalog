import { ReactComponent as ArrowIcon } from '../../assets/images/arrow.svg'
import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import ProductPrice from '../../components/ProductPrice';
import { Product } from '../../types/product';

import './styles.css';
import api from '../../util/requests';

type UrlParams = {
    productId: string;
}

const ProductDetails = () => {

    const { productId } = useParams<UrlParams>();

    const[product, setProduct] = useState<Product>();

    useEffect(() => {
        api
            .get(`/products/${productId}`)
            .then(response => {
                setProduct(response.data);
            })
            .catch((err) => {
                console.log("Erro ao buscar produto", err)
            })
    }, [productId])

    return (
        <div className='product-detail-container'>
            <div className='product-detail-card base-card'>
                <Link to={'/products'}>
                    <div className='goback-container'>
                        <ArrowIcon className='left-arrow' />
                        <h2>VOLTAR</h2>
                    </div>
                </Link>
                <div className='row'>
                    <div className='col-xl-6'>
                        <div className='img-container'>
                            <img src={product?.imgUrl} alt='tenis' />
                        </div>
                        <div className='name-price-container'>
                            <h1>{product?.name}</h1>
                            { product && <ProductPrice price={product?.price} />}
                        </div>
                    </div>
                    <div className='col-xl-6'>
                        <div className='description-container'>
                            <h2>Descrição do produto</h2>
                            <p>{product?.description}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProductDetails;