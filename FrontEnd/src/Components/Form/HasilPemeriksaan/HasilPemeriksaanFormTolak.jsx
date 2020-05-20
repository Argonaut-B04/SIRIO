import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SirioConfirmButton from "../../Button/ActionButton/SirioConfirmButton";
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import {NavLink, Redirect} from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class HasilPemeriksaanFormTolak extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            feedback: "",
            redirect: false,
            errorFeedback: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitButton = this.submitButton.bind(this);
    }

    componentDidMount() {
        this.props.contentFinishLoading()
    }

    validateFeedback(fokusFeedback) {
        var errorFeedback = "";
        var containLetter = /.*[a-zA-Z].*/;

        if (fokusFeedback === null || fokusFeedback === "") {
            errorFeedback = "";
        } else if (!fokusFeedback.match(containLetter)) {
            errorFeedback = "Feedback perlu mengandung huruf";
        } else if (fokusFeedback.length > 500) {
            errorFeedback = "Feedback maksimal 500 karakter";
        }

        if (this.state.errorFeedback !== errorFeedback) {
            this.setState({
                errorFeedback: errorFeedback
            })
        }
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
        const { name, value } = event.target;
        this.setState(
            {
                [name]: value
            }
        );

        this.validateFeedback(value);
    }

    handleSubmit(event) {
        event&&event.preventDefault();
        const persetujuan = {
            id: this.props.location.state.id,
            status: "3",
            feedback: this.state.feedback
        };

        if (this.state.feedback !== null && this.state.feedback !== "" && this.state.errorFeedback === "") {
            this.props.contentStartLoading();
            this.props.changeLoadingBody("Mengirim data ke server");
            HasilPemeriksaanService.setujuiHasilPemeriksaan(persetujuan)
                .then(() => this.setRedirect());

            this.props.contentFinishLoading();
        }
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
                    required: true,
                    value: this.state.feedback,
                    placeholder: "Feedback hasil pemeriksaan",
                    validation: this.state.errorFeedback
                }
            ]
        )
    }

    submitButton() {
        var tombolSimpan =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Simpan
            </SirioButton>;
        if (this.state.feedback !== null && this.state.feedback !== "" && this.state.errorFeedback === "") {
            tombolSimpan =
                <SirioConfirmButton
                    purple
                    recommended
                    classes="mx-1"
                    modalTitle="Apakah anda yakin untuk menolak hasil pemeriksaan?"
                    onConfirm={this.handleSubmit}
                    customConfirmText="Ya, Tolak"
                    customCancelText="Batal"
                    closeOnConfirm
                >
                    Simpan
                </SirioConfirmButton>
        }
        return (
            <div>
                {tombolSimpan}
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
                    onSubmit={(event) => event.preventDefault()}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}

export default withRouter(HasilPemeriksaanFormTolak);