import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormUbahBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            keterangan: "",
            lampiran: "",
            id: "",
            submitable: true,
            redirect: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataBuktiPelaksanaan = this.renderDataBuktiPelaksanaan.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderDataBuktiPelaksanaan();
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
                    editSuccess: true
                }
            }} />
        }
    };

    async renderDataBuktiPelaksanaan() {
        const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);
        
        this.setState({
            id: response.data.result.id,
            keterangan: response.data.result.keterangan,
            lampiran: response.data.result.lampiran
        })
    }

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
        const buktiPelaksanaan = {
            id: this.state.id,
            keterangan: this.state.keterangan,
            lampiran: this.state.lampiran
        };
        BuktiPelaksanaanService.editBukti(buktiPelaksanaan)
            .then(() => this.setRedirect());
    }

    componentDidUpdate(prevProps, prevState) {
        var submitable = true;
        var validating = false;
        submitable = this.validateRequired();
        if (prevState.keterangan !== this.state.keterangan) {
            submitable = this.validateKeterangan() && submitable;
            validating = true;
        }
        if (prevState.lampiran !== this.state.lampiran) {
            submitable = this.validateLampiran() && submitable;
            validating = true;
        }
        if (validating) {
            if (this.state.submitable !== submitable) {
                this.setState({
                    submitable: submitable
                })
            }
        }
    }

    validateRequired() {
        var submitable = true;
        const required = [this.state.keterangan, this.state.lampiran];
        for (let i = 0; i < required.length; i++) {
            submitable = submitable && (required[i] !== null && required[i] !== "");
        }
        return submitable;
    }

    validateKeterangan() {
        var submitable = true;
        const varKeterangan = this.state.keterangan;
        var errorKeterangan;
        if (varKeterangan.length < 1) {
            submitable = false;
            errorKeterangan = "Keterangan wajib diisi";
        } else if (varKeterangan.length > 125) {
            submitable = false;
            errorKeterangan = "Keterangan tidak boleh lebih dari 125 karakter";
        }
        if (this.state.errorKeterangan !== errorKeterangan) {
            this.setState({
                errorKeterangan: errorKeterangan
            })
        }
        return submitable;
    }

    validateLampiran() {
        var submitable = true;
        const varLampiran = this.state.lampiran;
        var errorLampiran;
        if (varLampiran.length < 1) {
            submitable = false;
            errorLampiran = "Lampiran wajib diisi";
        } else if (!(varLampiran.includes("https://"))) {
            submitable = false;
            errorLampiran = "Lampiran harus berupa link url";
        } else if (varLampiran.length > 255) {
            submitable = false;
            errorLampiran = "Lampiran tidak boleh lebih dari 255 karakter";
        }
        if (this.state.errorLampiran !== errorLampiran) {
            this.setState({
                errorLampiran: errorLampiran
            })
        }
        return submitable;
    }

    inputDefinition() {
        return (
            [
                {
                    label: "Keterangan*",
                    required: true,
                    handleChange: this.handleChange,
                    validation: this.state.errorKeterangan,
                    type: "textarea",
                    name: "keterangan",
                    value: this.state.keterangan,
                    placeholder: "Masukan keterangan bukti"
                }, {
                    label: "Lampiran*",
                    required: true,
                    handleChange: this.handleChange,
                    validation: this.state.errorLampiran,
                    type: "textarea",
                    name: "lampiran",
                    value: this.state.lampiran,
                    placeholder: "Masukan lampiran bukti"
                }
            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple 
                             recommended={this.state.submitable}
                             disabled={!this.state.submitable}
                             classes="mx-1"
                             onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                             type="button"
                             classes="mx-1"
                             onClick={() => window.location.href = "/bukti-pelaksanaan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Ubah Bukti Pelaksanaan"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}
export default withRouter(FormUbahBukti);