import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import {NavLink, Redirect} from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class HasilPemeriksaanFormTolak extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            feedback: "",
            redirect: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/hasil-pemeriksaan",
                state: {
                    tolakSuccess: true
                }
            }} />
        }
    };

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    handleSubmit(event) {
        event.preventDefault();
        const persetujuan = {
            id: this.props.location.state.id,
            status: "3",
            feedback: this.state.feedback
        };
        HasilPemeriksaanService.setujuiHasilPemeriksaan(persetujuan)
            .then(() => this.setRedirect());
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                label: "Feedback",
                handleChange: this.handleChange,
                type: "textarea",
                name: "feedback",
                value: this.state.feedback,
                placeholder: "Feedback hasil pemeriksaan"
                }
            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                             classes="mx-1"
                             onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/detail",
                    state: {
                        id: this.props.location.state.id
                    }
                }}>
                    <SirioButton
                        purple
                        classess="mx-1"
                    >
                        Batal
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Feedback Penolakan"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}

export default withRouter(HasilPemeriksaanFormTolak);