import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService';
import { NavLink, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormUbahBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            keterangan: "",
            lampiran: "",
            id: "",
            keteranganRekomendasi: "",
            submitable: true,
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

    validateKeterangan(fokusKeterangan) {
        var errorKeterangan = "";
        if (fokusKeterangan === null || fokusKeterangan === "") {
            errorKeterangan = "Keterangan bukti harus diisi";
        } else if (fokusKeterangan.length > 125) {
            errorKeterangan = "Keterangan tidak boleh lebih dari 125 karakter";
        }
        
        this.setState({
            errorKeterangan: errorKeterangan
        })
    }

    validateLampiran(fokusLampiran) {
        var errorLampiran = "";
        var linkOnly = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.%]+$/;
        if (fokusLampiran === null || fokusLampiran === "") {
            errorLampiran = "Lampiran bukti harus diisi";
        } else if (!fokusLampiran.match(linkOnly)) {
            errorLampiran = "Lampiran harus berupa link url";
        } else if (fokusLampiran.length > 255) {
            errorLampiran = "Lampiran tidak boleh lebih dari 255 karakter";
        }
        
        this.setState({
            errorLampiran: errorLampiran
        })
    }

    submitable() {
        return this.state.errorKeterangan === "" &&
            this.state.errorLampiran === "" &&
            (this.state.keterangan !== null && this.state.keterangan !== "") &&
            (this.state.lampiran !== null && this.state.lampiran !== "");
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
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);
        
        // Mengubah isi dari loader
        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            id: response.data.result.id,
            keterangan: response.data.result.keterangan,
            lampiran: response.data.result.lampiran,
            keteranganRekomendasi: response.data.result.keteranganRekomendasi
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
            case "keterangan":
                this.validateKeterangan(value);
                break;
            case "lampiran":
                this.validateLampiran(value);
                break;
        }
    }

    handleSubmit(event) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");

        event.preventDefault();
        if (this.submitable()) {
            const buktiPelaksanaan = {
                id: this.state.id,
                keterangan: this.state.keterangan,
                lampiran: this.state.lampiran
            };
            BuktiPelaksanaanService.editBukti(buktiPelaksanaan)
                .then(() => this.setRedirect());
        }

        this.props.contentFinishLoading();
    }

    inputDefinition() {
        return ([
            {
                label: "Rekomendasi",
                customInput: <p>{this.state.keteranganRekomendasi}</p>
            }, {
                label: "Keterangan",
                required: true,
                handleChange: this.handleChange,
                type: "textarea",
                name: "keterangan",
                value: this.state.keterangan,
                placeholder: "Masukan keterangan bukti",
                errormessage: this.state.errorKeterangan
            }, {
                label: "Lampiran",
                required: true,
                handleChange: this.handleChange,
                type: "textarea",
                name: "lampiran",
                value: this.state.lampiran,
                placeholder: "Masukan lampiran bukti (berupa link url)",
                errormessage: this.state.errorLampiran
            }
        ])
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
        if (this.submitable()) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event) => this.handleSubmit(event)}
                >
                    Simpan
                </SirioButton>
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
                    title="Form Ubah Bukti Pelaksanaan"
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
export default withRouter(FormUbahBukti);