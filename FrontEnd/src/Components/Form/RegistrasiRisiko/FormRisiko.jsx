import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import SopService from '../../../Services/SopService';
import { Redirect, withRouter } from 'react-router-dom';

class FormRisiko extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "",
            kategori: "",
            sop: "",
            detailUraian: "",
            ketentuanSampel: "",
            metodologi: "",
            deskripsi: "",
            sopOptionList: [],
            redirect: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.renderSopOption = this.renderSopOption.bind(this);
        this.submitable = this.submitable.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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
                    addSuccess: true
                }
            }} />
        }
    };

    validateNama(fokusNama, name) {
        var hasilError;
        if (fokusNama.length < 1 || fokusNama === null || fokusNama === "") {
            hasilError = "Nama tidak boleh kosong";
        } else if (fokusNama.length > 50) {
            hasilError = "Nama melebihi 50 karakter";
        } else {
            hasilError = "";
        }
        // if(fokusNama.match(".*[1234567890!-@#$%^&*()_+{}:.,[]|>/=<?]+.*")){
        //     submitable = false;
        //     errorNama = "Nama hanya boleh mengandung huruf";
        // }
        this.setState({
            [name]: fokusNama,
            errorNama: hasilError
        })
    }

    validateDU(fokusDU, name) {
        var hasilError;
        if (fokusDU.length > 500) {
            hasilError = "Detail Uraian Risiko terlalu panjang!";
        } else {
            hasilError = "";
        }
        this.setState({
            [name]: fokusDU,
            errorDU: hasilError
        })
    }

    validateMetod(fokusMetod, name) {
        var hasilError;
        if (fokusMetod.length > 50) {
            hasilError = "Metodologi terlalu panjang!";
        } else {
            hasilError = "";
        }
        this.setState({
            [name]: fokusMetod,
            errorMetod: hasilError
        })
    }

    validateDesc(fokusDesc, name) {
        var hasilError;
        if (fokusDesc.length > 500) {
            hasilError = "Deskripsi terlalu panjang!";
        } else {
            hasilError = "";
        }
        this.setState({
            [name]: fokusDesc,
            errorDesc: hasilError
        })
    }

    validateKS(fokusKS, name) {
        var hasilError;
        if (fokusKS.length > 500) {
            hasilError = "Ketentuan Sampel terlalu panjang!";
        } else {
            hasilError = "";
        }
        this.setState({
            [name]: fokusKS,
            errorKS: hasilError
        })
    }

    submitable() {
        return this.state.errorNama === "" &&
            (this.state.detailUraian === null || this.state.errorDU === "") &&
            (this.state.metodologi === null || this.state.errorMetod === "") &&
            (this.state.deskripsi === null || this.state.errorDesc === "") &&
            (this.state.ketentuanSampel === null || this.state.errorKS === "") &&
            (this.state.nama !== null && this.state.nama !== "") &&
            (this.state.kategori !== null) &&
            (this.state.sop !== null);
    }

    componentDidMount() {
        this.renderSopOption();
    }

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

    // Fungsi untuk mengubah state ketika isi dari input diubah
    handleChange(event) {
        // Gunakan name dan value nya
        const { name, value } = event.target;
        switch (name) {
            case "nama":
                this.validateNama(value, name);
                break;
            case "detailUraian":
                this.validateDU(value, name);
                break;
            case "metodologi":
                this.validateMetod(value, name);
                break;
            case "deskripsi":
                this.validateDesc(value, name);
                break;
            case "ketentuanSampel":
                this.validateKS(value, name);
                break;
            default:
                break;
        }
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi unu wajib ada jika membuat field tipe select
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event) {
        // event.preventDefault wajib ada
        event.preventDefault();
        if (this.submitable()) {
            const risiko = {
                nama: this.state.nama,
                kategori: this.state.kategori,
                sop: this.state.sop,
                detailUraian: this.state.detailUraian,
                metodologi: this.state.metodologi,
                deskripsi: this.state.deskripsi,
                ketentuanSampel: this.state.ketentuanSampel
            }
            RegistrasiRisikoService.submitChanges(risiko)
                .then(() => this.setRedirect());
        }
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        const { errorNama, errorDU, errorDesc, errorMetod, errorKS } = this.state;
        return (
            [
                {
                    label: "Nama Risiko",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Masukan nama risiko",
                    required: true,
                    errormessage: errorNama
                }, {
                    label: "Kategori Risiko",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "kategori",
                    value: this.state.kategori,
                    required: true,
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
                    label: "Referensi SOP",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "sop",
                    value: this.state.sop,
                    required: true,
                    optionList: this.state.sopOptionList
                }, {
                    label: "Detail Uraian Risiko",
                    handleChange: this.handleChange,
                    disabled: (this.state.kategori !== 3),
                    type: "textarea",
                    name: "detailUraian",
                    value: this.state.detailUraian,
                    placeholder: "Masukan detail uraian risiko",
                    errormessage: errorDU
                }, {
                    label: "Metodologi",
                    handleChange: this.handleChange,
                    disabled: this.state.kategori !== 3,
                    type: "text",
                    name: "metodologi",
                    value: this.state.metodologi,
                    placeholder: "Masukan metodologi risiko",
                    errormessage: errorMetod
                }, {
                    label: "Deskripsi",
                    handleChange: this.handleChange,
                    disabled: this.state.kategori !== 3,
                    type: "textarea",
                    name: "deskripsi",
                    value: this.state.deskripsi,
                    placeholder: "Masukan deskripsi risiko",
                    errormessage: errorDesc
                }, {
                    label: "Ketentuan Sampel",
                    handleChange: this.handleChange,
                    disabled: this.state.kategori !== 3,
                    type: "textarea",
                    name: "ketentuanSampel",
                    value: this.state.ketentuanSampel,
                    placeholder: "Masukan ketentuan sampel risiko",
                    errormessage: errorKS
                }
            ]
        )
    }

    submitButton() {
        var tombolSimpan =
            <SirioButton
                purple
                disabled
                classes="mx-2"
            >
                Simpan
            </SirioButton>;
        if (this.submitable()) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-2"
                    onClick={(event) => this.handleSubmit(event)}
                >
                    Simpan
                </SirioButton>
        }
        return (
            <div>
                {tombolSimpan}
                <SirioButton purple
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
                    title="Form Risiko"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}
export default withRouter(FormRisiko);