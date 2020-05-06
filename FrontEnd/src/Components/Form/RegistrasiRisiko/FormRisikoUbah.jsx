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
            submitable: true,
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

    componentDidUpdate(prevProps, prevState) {
        var submitable = true;
        var validating = false;

        submitable = this.validateRequired();
        if (prevState.nama !== this.state.nama) {
            submitable = this.validateNama() && submitable;
            validating = true;
        }

        if (prevState.kategori !== this.state.kategori) {
            submitable = this.validateKategori() && submitable;
            validating = true;

        }

        if (prevState.sop !== this.state.sop) {
            submitable = this.validateSop() && submitable;
            validating = true;

        }

        if (prevState.detailUraian !== this.state.detailUraian) {
            submitable = this.validateDetailUraian() && submitable;
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
        const required = [this.state.nama, this.state.kategori, this.state.sop];
        for (let i=0; i<required.length; i++) {
            submitable = submitable && (required[i] !== null && required[i] !== "");
        }
        return submitable;
    }
    
    validateNama() {
        var submitable = true;
        const fokusNama = this.state.nama;
        var errorNama;
        if (fokusNama.length < 1 || fokusNama === null || fokusNama === "") {
            submitable = false;
            errorNama = "Nama tidak boleh kosong";
        } else if (fokusNama.length > 50) {
            submitable = false;
            errorNama = "Nama melebihi 50 karakter";
        }
        // if(fokusNama.match(".*[1234567890!-@#$%^&*()_+{}:.,[]|>/=<?]+.*")){
        //     submitable = false;
        //     errorNama = "Nama hanya boleh mengandung huruf";
        // }
        if (this.state.errorNama !== errorNama) {
            this.setState({
                errorNama: errorNama
            })
        }
        return submitable;
    }

    validateKategori() {
        var submitable = true;
        const fokusKategori = this.state.kategori;
        var errorKategori;
        if (fokusKategori.length < 1) {
            submitable = false;
            errorKategori = "Kategori Risiko tidak boleh kosong!";
        }
        if (this.state.errorKategori !== errorKategori) {
            this.setState({
                errorKategori: errorKategori
            })
        }
        return submitable;
    }

    validateSop() {
        var submitable = true;
        const fokusSop = this.state.sop;
        var errorSop;
        if (fokusSop.length < 1) {
            submitable = false;
            errorSop = "Referensi SOP tidak boleh kosong!";
        } 
        if (this.state.errorSop !== errorSop) {
            this.setState({
                errorSop: errorSop
            })
        }
        return submitable;
    }

    validateDetailUraian() {
        var submitable = true;
        const fokusDU = this.state.detailUraian;
        var errorDU;
        if (fokusDU === null) {
            submitable = true;
        } else if (fokusDU != null || fokusDU !== "" || fokusDU.length > 1) {
            if (fokusDU.length > 500) {
                submitable = false;
                errorDU = "Detail Uraian Risiko terlalu panjang!";
            }
        }
        if (this.state.errorDU !== errorDU) {
            this.setState({
                errorDU: errorDU
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
            detailUraian: response.data.result.detailUraian,
            metodologi: response.data.result.metodologi,
            ketentuanSampel: response.data.result.ketentuanSampel,
            deskripsi: response.data.result.deskripsi
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
            }
        // }
    // }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Nama Risiko",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "nama",
                    required: true,
                    validation: this.state.errorNama,
                    value: this.state.nama,
                    placeholder: "Masukan nama risiko"
                }, {
                    label: "Kategori Risiko",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "kategori",
                    validation: this.state.errorKategori,
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
                    validation: this.state.errorSop,
                    optionList: this.state.sopOptionList
                }, {
                    label: "Detail Uraian Risiko",
                    disabled: this.state.kategori != 3,
                    handleChange: this.handleChange,
                    validation: this.state.errorDU,
                    // disabled: {this.state.kategori != 3 ? disabled : enabled},
                    type: "textarea",
                    name: "detailUraian",
                    value: this.state.detailUraian,
                    placeholder: "Masukan detail uraian risiko"
                }, {
                    label: "Metodologi",
                    disabled: this.state.kategori != 3,
                    handleChange: this.handleChange,
                    // validation: this.state.errorDU,
                    type: "text",
                    name: "metodologi",
                    value: this.state.metodologi,
                    placeholder: "Masukan metodologi risiko"
                }, {
                    label: "Deskripsi",
                    disabled: this.state.kategori != 3,
                    handleChange: this.handleChange,
                    // validation: this.state.errorDU,
                    type: "textarea",
                    name: "deskripsi",
                    value: this.state.deskripsi,
                    placeholder: "Masukan deskripsi risiko"
                }, {
                    label: "Ketentuan Sampel",
                    disabled: this.state.kategori != 3,
                    handleChange: this.handleChange,
                    // validation: this.state.errorDU,
                    type: "textarea",
                    name: "ketentuanSampel",
                    value: this.state.ketentuanSampel,
                    placeholder: "Masukan ketentuan sampel risiko"
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