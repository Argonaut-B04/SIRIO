import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormTolakBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            buktiPelaksanaan: {},
            feedback: "",
            redirect: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataBuktiPelaksanaan = this.renderDataBuktiPelaksanaan.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    componentDidMount() {
        this.renderDataBuktiPelaksanaan();
    }

    validateFeedback(fokusFeedback) {
        var errorFeedback = "";
        if (fokusFeedback === null || fokusFeedback === "") {
            errorFeedback = "Feedback penolakan bukti harus diisi";
        } else if (fokusFeedback.length > 125) {
            errorFeedback = "Feedback tidak boleh lebih dari 125 karakter";
        }
        
        this.setState({
            errorFeedback: errorFeedback
        })
    }

    submitable() {
        return this.state.errorFeedback === "" &&
            (this.state.feedback !== null && this.state.feedback !== "");
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/bukti-pelaksanaan",
                state: {
                    rejectSuccess: true
                }
            }} />
        }
    };

    async renderDataBuktiPelaksanaan() {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);
        
        // Mengubah isi dari loader
        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            id: response.data.result.id,
            keterangan: response.data.result.keterangan,
            lampiran: response.data.result.lampiran
        }, this.props.contentFinishLoading()) // Setelah jeda waktu, hentikan loader
    }

    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        );

        switch (name) {
            case "feedback":
                this.validateFeedback(value);
                break;
        }
    }

    handleSubmit(event) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");

        event && event.preventDefault();
        if (this.submitable()) {
            const buktiPelaksanaan = {
                status: 3,
                statusRekomendasi: 6,
                feedback: this.state.feedback
            };
            BuktiPelaksanaanService.setStatusBukti(this.state.id, buktiPelaksanaan)
                .then(() => this.setRedirect());
        }

        this.props.contentFinishLoading();
    }


    inputDefinition() {
        return ([
            {
                label: "Keterangan",
                customInput: <p>{this.state.keterangan}</p>
            }, {
                label: "Lampiran",
                customInput: <p>{this.state.lampiran}</p>
            }, {
                label: "Feedback",
                required: true,
                handleChange: this.handleChange,
                type: "textarea",
                name: "feedback",
                value: this.state.feedback,
                placeholder: "Feedback penolakan bukti pelaksanaan",
                errormessage: this.state.errorFeedback
            }
        ])
    }

    submitButton(cell, row) {
        var tombolSimpan =
            <SirioConfirmButton
                purple
                disabled
                classes="mx-1"
            >
                Simpan
            </SirioConfirmButton>;
        if (this.submitable()) {
            tombolSimpan = 
                <SirioConfirmButton
                    purple
                    recommended
                    type="button"
                    classes="mx-1"
                    modalTitle="Apakah anda yakin untuk menolak bukti pelaksanaan?"
                    onConfirm={this.handleSubmit}
                    customConfirmText="Ya, Tolak"
                    customCancelText="Batal"
                >
                    Simpan
                </SirioConfirmButton>
        }
        return (
            <div>
                {tombolSimpan}
                <NavLink to={{
                    pathname: "/bukti-pelaksanaan/detail",
                    state: {
                        id: this.props.location.state.id
                    }
                }}>
                    <SirioButton
                        purple
                        classes="mx-1"
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
                    title="Form Feedback Penolakan Bukti Pelaksanaan"
                    data={this.data}
                    id='id'
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}
export default withRouter(FormTolakBukti);