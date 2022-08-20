import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import ButtonIcon from '../../../../components/ButtonIcon';
import { requestBackendLogin } from '../../../../util/requests';
import './styles.css'

type FormData = {
    username: string;
    password: string;
}

const LoginCard = () => {

    const { register, handleSubmit, formState: {errors} } = useForm<FormData>();
    const onSubmit = (formData: FormData) => {
        requestBackendLogin(formData)
            .then(response => {
                console.log('SUCESSO', response);
            })
            .catch(err => {
                console.log('ERRO', err);
            })
    }

    return (
        <div className="base-card login-card">
            <h1>LOGIN</h1>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="mb-4">
                    <input
                        {...register("username", {
                            required: '*Campo obrigatório',
                            pattern: {
                                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                message: '*Email inválido'
                            }
                        })}
                        type="text"
                        className="form-control base-input"
                        placeholder="Email"
                        name="username"
                    />
                    <div className='invalid-feedback d-block'>{errors.username?.message}</div>
                </div>
                <div className="mb-2">
                    <input
                        {...register("password", {
                            required: '*Campo obrigatório'
                        })}
                        type="password"
                        className="form-control base-input "
                        placeholder="Password"
                        name="password"
                    />
                    <div className='invalid-feedback d-block'>{errors.password?.message}</div>
                </div>
                <Link to="/admin/auth/recover" className="login-link-recover">
                    Esqueci a senha
                </Link>
                <div className="login-submit">
                    <ButtonIcon buttonTitle='Fazer login' />
                </div>
                <div className="signup-container">
                    <span className="not-registered">Não tem Cadastro?</span>
                    <Link to="/admin/auth/signup" className="login-link-register">
                        CADASTRAR
                    </Link>
                </div>
            </form>
        </div>
    )
}

export default LoginCard;