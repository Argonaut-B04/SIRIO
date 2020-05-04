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
            redirect: false,
            submitable: false,
            errorFeedback: "",
            daftarKomponen: "",
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.renderDataHasilPemeriksaan = this.renderDataHasilPemeriksaan.bind(this);
        this.submitButton = this.submitButton.bind(this);
    }

    componentDidMount() {
        this.renderDataHasilPemeriksaan();
    }

    async renderDataHasilPemeriksaan() {
        const response = await HasilPemeriksaanService.getHasilPemeriksaan(this.props.location.state.id);

        var daftarKomponen = <p>
                {response.data.result.daftarKomponenPemeriksaan.map((komponen, index) =>
                    <p className="text-center p-0 m-0">{index+1}. {komponen.risiko.nama} </p>
                )}
            </p>;

        // var daftarKomponen = "";
        // response.data.result.daftarKomponenPemeriksaan.map((komponen, index) => {
        //     const indexX = index + 1;
        //     const kata = "(" + indexX + ") " + komponen.risiko.nama + "; ";
        //     daftarKomponen += "\n\n" + kata;
        // });

        this.setState({
            daftarKomponen: daftarKomponen
        })
    }

    componentDidUpdate(prevProps, prevState) {
        var submitable = this.state.feedback !== null && this.state.feedback !== "";

        submitable = submitable && this.validateFeedback();

        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }
    }

    validateFeedback() {
        var submitable = true;
        var fokusFeedback = this.state.feedback;
        var errorFeedback;
        var containLetter = /.*[a-zA-Z].*/;
        if (!fokusFeedback.match(containLetter)) {
            submitable = false;
            errorFeedback = "Feedback perlu mengandung huruf";
        } else if (fokusFeedback.length > 500) {
            submitable = false;
            errorFeedback = "Feedback maksimal 500 karakter";
        }

        if (this.state.errorFeedback !== errorFeedback) {
            this.setState({
                errorFeedback: errorFeedback
            })
        }
        return submitable;
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
        event&&event.preventDefault();
        const persetujuan = {
            id: this.props.location.state.id,
            status: "3",
            feedback: this.state.feedback
        };

        if (this.state.submitable) {
            HasilPemeriksaanService.setujuiHasilPemeriksaan(persetujuan)
                .then(() => this.setRedirect());
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
                    value: this.state.feedback,
                    placeholder: "Feedback hasil pemeriksaan",
                    validation: this.state.errorFeedback
                }
            ]
        )
    }

    submitButton() {
        return (
            <div>
                {/*<SirioButton purple*/}
                {/*             recommended={this.state.submitable}*/}
                {/*             disabled={!this.state.submitable}*/}
                {/*             classes="mx-1"*/}
                {/*             onClick={(event)  => this.handleSubmit(event)}>*/}
                {/*    Simpan*/}
                {/*</SirioButton>*/}
                <SirioConfirmButton
                    purple
                    classes="m-1"
                    modalTitle="Apakah anda yakin untuk menolak hasil pemeriksaan?"
                    onConfirm={this.handleSubmit}
                    customConfirmText="Ya, Tolak"
                    customCancelText="Batal"
                    closeOnConfirm
                >
                    Simpan
                </SirioConfirmButton>
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