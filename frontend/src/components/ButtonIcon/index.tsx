import './styles.css'

import ArrowIcon from '../../assets/images/arrow.svg'

type Props = {
    buttonTitle: string;
}

const ButtonIcon = ( { buttonTitle }: Props ) => {
    return (
        <div className='btn-container'>
            <button className="btn btn-primary">
                <h6>{buttonTitle}</h6>
            </button>
            <div className='btn-icon-container'>
                <img src={ArrowIcon} alt='seta' />
            </div>
        </div>
    )
}

export default ButtonIcon