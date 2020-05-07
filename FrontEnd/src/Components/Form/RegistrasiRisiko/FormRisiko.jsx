import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import SopService from '../../../Services/SopService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

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
            submitable: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.renderSopOption = this.renderSopOption.bind(this);
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

    componentDidUpdate(prevProps, prevState) {
        var submitable = true;
        var validating = false;

        submitable = this.validateRequired();
        console.log(submitable)
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
            console.log(submitable)
            validating = true;

        }

        if (prevState.metodologi !== this.state.metodologi) {
            submitable = this.validateMetodologi() && submitable;
            console.log(submitable)

            validating = true;

        }

        if (prevState.deskripsi !== this.state.deskripsi) {
            submitable = this.validateDeskripsi() && submitable;
            console.log(submitable)

            validating = true;

        }

        if (prevState.ketentuanSampel !== this.state.ketentuanSampel) {
            submitable = this.validateKetentuanSampel() && submitable;
            console.log(submitable)

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
        if (fokusDU.length > 500) {
            submitable = false;
            errorDU = "Detail Uraian Risiko terlalu panjang!";
        }
        if (this.state.errorDU !== errorDU) {
            this.setState({
                errorDU: errorDU
            })
        }
        return submitable;
    }

    validateKetentuanSampel() {
        var submitable = true;
        const fokusKS = this.state.ketentuanSampel;
        var errorKS;
        if (fokusKS.length > 500) {
            submitable = false;
            errorKS = "Ketentuan Sampel terlalu panjang!";
        }
        if (this.state.errorKS !== errorKS) {
            this.setState({
                errorKS: errorKS
            })
        }
        return submitable;
    }

    validateDeskripsi() {
        var submitable = true;
        const fokusDesc = this.state.deskripsi;
        var errorDesc;
        if (fokusDesc.length > 500) {
            submitable = false;
            errorDesc = "Deskripsi terlalu panjang!";
        }
        if (this.state.errorDesc !== errorDesc) {
            this.setState({
                errorDesc: errorDesc
            })
        }
        return submitable;
    }

    validateMetodologi() {
        var submitable = true;
        const fokusMetod = this.state.metodologi;
        var errorMetod;
        if (fokusMetod.length > 50) {
            submitable = false;
            errorMetod = "Metodologi terlalu panjang!";
        }
        if (this.state.errorMetod !== errorMetod) {
            this.setState({
                errorMetod: errorMetod
            })
        }
        return submitable;
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
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        if (typeof event.target.checked === "boolean") {
            this.setState(
                {
                    [event.target.name]
                        : event.target.checked
                }
            )
        } else {
            this.setState(
                {
                    [event.target.name]
                        : event.target.value
                }
            )
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
        // if (this.state.submitable) {
        //     const response = await RegistrasiRisikoService.checkRisikoExist(this.state.nama);
        //     console.log(response.data.result)
        //     if (response.data.result) {
        //         const errorNama = "Nama sudah terdaftar";
        //         if (this.state.errorNama !== errorNama) {
        //             console.log("masuk")
        //             this.setState({
        //                 errorNama: errorNama
        //             })
        //         }
        //     } else {
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
    //     }
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
                    type: "text",
                    required: true,
                    // validation: this.state.errorNama,
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Masukan nama risiko"
                }, {
                    label: "Kategori Risiko",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    required: true,
                    validation: this.state.errorKategori,
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
                    label: "Referensi SOP",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    validation: this.state.errorSop,
                    required: true,
                    name: "sop",
                    value: this.state.sop,
                    optionList: this.state.sopOptionList
                }, {
                    label: "Detail Uraian Risiko",
                    handleChange: this.handleChange,
                    validation: this.state.errorDU,
                    disabled: this.state.kategori !== 3,
                    type: "textarea",
                    name: "detailUraian",
                    value: this.state.detailUraian,
                    placeholder: "Masukan detail uraian risiko"
                }, {
                    label: "Metodologi",
                    handleChange: this.handleChange,
                    // validation: this.state.errorMetod,
                    disabled: this.state.kategori !== 3,
                    type: "textarea",
                    name: "metodologi",
                    value: this.state.metodologi,
                    placeholder: "Masukan metodologi risiko"
                }, {
                    label: "Deskripsi",
                    handleChange: this.handleChange,
                    validation: this.state.errorDesc,
                    disabled: this.state.kategori !== 3,
                    type: "textarea",
                    name: "deskripsi",
                    value: this.state.deskripsi,
                    placeholder: "Masukan deskripsi risiko"
                }, {
                    label: "Ketentuan Sampel",
                    handleChange: this.handleChange,
                    validation: this.state.errorKS,
                    disabled: this.state.kategori !== 3,
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
                    classes="mx-2"
                    onClick={(event)  => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
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