import ContentLoader from "react-content-loader"

import './styles.css';

const CardLoader = () => (
    <div className="cardloader-container">
        <ContentLoader
            speed={2}
            width={300}
            height={460}
            viewBox="0 0 320 460"
            backgroundColor="#ecebeb"
            foregroundColor="#d6d2d2"
        >
            <rect x="0" y="0" rx="10" ry="10" width="300" height="300" />
        </ContentLoader>
    </div>
)

export default CardLoader