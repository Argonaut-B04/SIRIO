import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SopService from '../../../Services/SopService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormUbahSOP extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            id: "",
            judul: "",
            kategori: "",
            linkDokumen: "",
            redirect: false,
            submitable: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataSOP = this.renderDataSop.bind(this);
    }

    componentDidMount() {
        this.renderDataSop();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/manager/sop",
                state: {
                    editSuccess: true
                }
            }} />
        }
    };


    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    // Fungsi untuk mengubah state ketika isi dropdown diubah
    // Fungsi ini wajib ada jika membuat field tipe select
    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    componentDidUpdate() {
        var submitable = true;

        submitable = this.validateJudul() && this.validateKategori() && this.validateLink();

        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }
    }

    async renderDataSop() {
        const response = await SopService.getSOPDetail(this.props.location.state.id);

        this.setState({
            id: response.data.result.idSop,
            judul: response.data.result.judul,
            kategori: response.data.result.kategori,
            linkDokumen: response.data.result.linkDokumen

        })
    }

    validateRequired() {
        var submitable = true;
        const required = [this.state.judul, this.state.kategori, this.state.linkDokumen];
        for (let i = 0; i < required.length; i++) {
            submitable = submitable && (required[i] !== null && required[i] !== "");
        }
        return submitable;
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event) {
        event.preventDefault();
        if(this.state.submitable){
            const sop = {
                id: this.state.id,
                judul: this.state.judul,
                kategori: this.state.kategori,
                linkDokumen: this.state.linkDokumen
            }
            SopService.editSOP(sop)
            .then(() => this.setRedirect());
            
        }
    }

    validateJudul() {
        var submitable = true;
        var errorJudul;
        const fokusJudul = this.state.judul
        if(fokusJudul.match(".*[1234567890!-@#$%^&*()_+{}:.,[]|>/=<?]+.*")){
            submitable = false;
            errorJudul = "Hanya boleh mengandung huruf";
        }
        if (fokusJudul.length < 2) {
            submitable = false;
            errorJudul = "Judul sop harus diisi";
        } 
        if (fokusJudul.length > 25) {
            submitable = false;
            errorJudul = "Nama kantor tidak boleh lebih dari 25 karakter";
        }
        if (this.state.errorJudul !== errorJudul) {
            this.setState({
                errorJudul: errorJudul
            })
        }
        return submitable;
    }

    validateKategori() {
        var submitable = true;
        var errorKat;
        const fokusKat = this.state.kategori
        if(fokusKat.match(".*[-@#!$%^&*()_+{}:.,[]|>/=<?]+.*")){
            submitable = false;
            errorKat = "Hanya boleh mengandung huruf dan angka";
        }
        if (fokusKat.length < 2) {
            submitable = false;
            errorKat = "Kategori harus diisi";
        } 
        if (fokusKat.length > 125) {
            submitable = false;
            errorKat = "Kategori tidak boleh lebih dari 125 karakter";
        }
        if (this.state.errorKat !== errorKat) {
            this.setState({
                errorKat: errorKat
            })
        }
        return submitable;
    }

    validateLink() {
        var submitable = true;
        var errorLink;
        const fokusLink = this.state.linkDokumen
        if(!fokusLink.includes("https://")){
            submitable = false;
            errorLink= "Hanya boleh berbentuk link";
        }
        if (fokusLink.length < 2) {
            submitable = false;
            errorLink = "Link SOP harus diisi";
        } 
        if (this.state.errorLink!== errorLink) {
            this.setState({
                errorLink: errorLink
            })
        }
        return submitable;
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Judul SOP",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "judul",
                    validation: this.state.errorJudul,
                    value: this.state.judul,
                    placeholder: "Masukan judul SOP"
                }, {
                    label: "Kategori SOP",
                    required: true,
                    validation: this.state.errorKat,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "kategori",
                    value: this.state.kategori,
                    placeholder: "Masukan kategori SOP"
                },{
                    label: "Link SOP",
                    required: true,
                    handleChange: this.handleChange,
                    type: "text",
                    name: "area",
                    validation: this.state.errorLink,
                    value: this.state.linkDokumen,
                    placeholder: "Masukan link dokumen SOP"
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
                    onClick={() => window.location.href = "/manager/sop"}>
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
                    title="Form Ubah SOP"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
             </>
        );
    }
}

export default withRouter (FormUbahSOP);