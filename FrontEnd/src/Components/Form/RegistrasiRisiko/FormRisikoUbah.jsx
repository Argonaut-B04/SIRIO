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
            detailUraian: "",
            ketentuanSampel: "",
            metodologi: "",
            deskripsi: "",
            sopOptionList: [],
            redirect: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataRisiko = this.renderDataRisiko.bind(this);
        this.renderSopOption = this.renderSopOption.bind(this);
        this.submitable = this.submitable.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderSopOption();
        this.renderDataRisiko();
    }

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
            (this.state.nama !== "" && this.state.nama !== "") &&
            (this.state.kategori !== "") &&
            (this.state.sop !== "");
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
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");
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
        }, this.props.contentFinishLoading())
    }

    async renderDataRisiko() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");
        const response = await RegistrasiRisikoService.getRisiko(this.props.location.state.id);

        this.setState({
            id: response.data.result.idRisiko,
            nama: response.data.result.namaRisiko,
            kategori: response.data.result.risikoKategori,
            sop: response.data.result.sop.idSop,
            detailUraian: response.data.result.detailUraian,
            metodologi: response.data.result.metodologi,
            ketentuanSampel: response.data.result.ketentuanSampel,
            deskripsi: response.data.result.deskripsi,
            errorNama: "",
            errorDU: "",
            errorMetod: "",
            errorDesc: "",
            errorKS: ""
        }, this.props.contentFinishLoading())
    }

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

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    handleSubmit(event) {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        // event.preventDefault wajib ada
        event.preventDefault();
        // if (this.state.submitable) {
        //     const response = await RegistrasiRisikoService.checkRisikoExist(this.state.nama);
        //     if (response.data.result) {
        //         const errorUsername = "Nama sudah terdaftar";
        //         if (this.state.errorUsername !== errorUsername) {
        //             this.setState({
        //                 errorUsername: errorUsername
        //             })
        //         }
        //     } else {
            if (this.submitable()) {
                const risiko = {
                    id: this.state.id,
                    nama: this.state.nama,
                    kategori: this.state.kategori,
                    sop: this.state.sop,
                    detailUraian: this.state.detailUraian,
                    metodologi: this.state.metodologi,
                    deskripsi: this.state.deskripsi,
                    ketentuanSampel: this.state.ketentuanSampel
                }
                RegistrasiRisikoService.ubahRisiko(risiko)
                    .then(() => this.setRedirect());
                    this.props.contentFinishLoading()
            }
        }
        // }
    // }

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
                    required: true,
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
                    label: "Referensi SOP",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "sop",
                    value: this.state.sop,
                    required: true,
                    optionList: this.state.sopOptionList
                }, {
                    label: "Detail Uraian Risiko",
                    disabled: this.state.kategori !== 3,
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "detailUraian",
                    value: this.state.detailUraian,
                    placeholder: "Masukan detail uraian risiko",
                    errormessage: errorDU
                }, {
                    label: "Metodologi",
                    disabled: this.state.kategori !== 3,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "metodologi",
                    value: this.state.metodologi,
                    placeholder: "Masukan metodologi risiko",
                    errormessage: errorMetod
                }, {
                    label: "Deskripsi",
                    disabled: this.state.kategori !== 3,
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "deskripsi",
                    value: this.state.deskripsi,
                    placeholder: "Masukan deskripsi risiko",
                    errormessage: errorDesc
                }, {
                    label: "Ketentuan Sampel",
                    disabled: this.state.kategori !== 3,
                    handleChange: this.handleChange,
                    // validation: this.state.errorDU,
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