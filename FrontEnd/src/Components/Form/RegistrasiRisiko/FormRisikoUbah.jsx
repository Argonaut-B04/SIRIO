import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import SopService from '../../../Services/SopService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormRisikoUbah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            id: "",
            nama: "",
            kategori: "",
            sop: "",
            komponen: "",
            sopOptionList: [],
            redirect: false
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataRisiko = this.renderDataRisiko.bind(this);
        this.renderSopOption = this.renderSopOption.bind(this);
    }

    componentDidMount() {
        this.renderSopOption();
        this.renderDataRisiko();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/registrasi-risiko",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };

    async renderSopOption() {
        const response = await SopService.getSopList();

        const sopOptionList = response.data.result.map(sop => {
            return (
                {
                    label: sop.judul,
                    value: sop.idSop
                }
            )
        });

        this.setState({
            sopOptionList: sopOptionList
        })
    }

    async renderDataRisiko() {
        const response = await RegistrasiRisikoService.getRisiko(this.props.location.state.id);

        this.setState({
            id: response.data.result.idRisiko,
            nama: response.data.result.namaRisiko,
            kategori: response.data.result.risikoKategori,
            sop: response.data.result.sop.idSop,
            komponen: response.data.result.komponen,
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

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    handleSubmit(event) {
        // event.preventDefault wajib ada
        event.preventDefault();
        const risiko = {
            id: this.state.id,
            nama: this.state.nama,
            kategori: this.state.kategori,
            sop: this.state.sop,
            komponen: this.state.komponen
        }
        RegistrasiRisikoService.ubahRisiko(risiko)
            .then(() => this.setRedirect());
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        console.log(this.state.sop);
        return (
            [
                {
                    label: "Nama Risiko*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Masukan nama risiko"
                }, {
                    label: "Kategori Risiko*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "kategori",
                    value: this.state.kategori,
                    optionList: [
                        {
                            label: "Kategori 1",
                            value: 1
                        }, {
                            label: "Kategori 2",
                            value: 2
                        }, {
                            label: "Kategori 3",
                            value: 3
                        }
                    ]
                }, {
                    label: "Referensi SOP*",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "sop",
                    value: this.state.sop,
                    optionList: this.state.sopOptionList
                }, {
                    label: "Komponen Risiko",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "komponen",
                    value: this.state.komponen,
                    placeholder: "Masukan komponen risiko"
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
                <SirioButton purple
                             classes="mx-1"
                             onClick={() => window.location.href = "/registrasi-risiko"}>
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
                    title="Form Ubah Risiko"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}
export default withRouter(FormRisikoUbah);