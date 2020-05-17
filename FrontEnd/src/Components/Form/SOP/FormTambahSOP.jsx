import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SopService from '../../../Services/SopService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormTambahSOP extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            judul: "",
            kategori: "",
            linkDokumen: "",
            redirect: false,
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    validateJudul(fokusJudul) {
        var errorJudul = "";
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (fokusJudul === null || fokusJudul === "") {
            errorJudul = "Judul harus diisi";
        } else if(!fokusJudul.match(letterOnly)){
            errorJudul = "Hanya boleh mengandung huruf";
        } else if (fokusJudul.length > 25) {
            errorJudul = "Nama kantor tidak boleh lebih dari 25 karakter";
        }
       
        this.setState({
            errorJudul: errorJudul
        })
       
    }

    validateKategori(fokusKat) {
        var errorKat = "";
        var letterOnly = /^[a-zA-Z\s]*$/;
        if (fokusKat === null || fokusKat === "") {
            errorKat = "Judul harus diisi";
        } else if(!fokusKat.match(letterOnly)){
            errorKat = "Hanya boleh mengandung huruf";
        } else if (fokusKat.length > 125) {
            errorKat = "Kategori tidak boleh lebih dari 125 karakter";
        }
        
        this.setState({
            errorKat: errorKat
        })
       
    }

    validateLink(fokusLink) {
        var errorLink = "";
        var link = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.%]+$/;
        if (fokusLink === null || fokusLink === "") {
            errorLink = "Link SOP  harus diisi";
        } else if(!fokusLink.match(link)){
            errorLink= "Tidak sesuai dengan format link url";
        }
        
        
        this.setState({
            errorLink: errorLink
        })
        
    }

    submitable() {
        return this.state.errorJudul === "" &&
            this.state.errorKat === "" &&
            this.state.errorLink === "" &&
            (this.state.judul !== null && this.state.judul !== "") &&
            (this.state.kategori !== null && this.state.kategori !== "") &&
            (this.state.linkDokumen !== null && this.state.linkDokumen !== "");
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/sop",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };


    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        const { name, value } = event.target;
        this.setState(
            {
                [name]
                    : value
            }
        );

        switch (name) {
            case "judul":
                this.validateJudul(value);
                break;
            case "kategori":
                this.validateKategori(value);
                break;
            case "linkDokumen":
                this.validateLink(value);
                break;
            
        }
    }

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    async handleSubmit(event) {
        event.preventDefault();
        if(this.submitable()){
            SopService.isExistSOP(this.state.judul)
            .then (response => {
                if(response.data.result){
                    this.setState({
                        errorJudul: "Judul SOP sudah ada di database"
                    })
                } else {
                    const sop = {
                        judul: this.state.judul,
                        kategori: this.state.kategori,
                        linkDokumen: this.state.linkDokumen
                    }
                    SopService.addSOP(sop)
                    .then(() => this.setRedirect());
                }
            })
        }
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
                    errormessage: this.state.errorJudul,
                    value: this.state.judul,
                    placeholder: "Masukan judul SOP"
                }, {
                    label: "Kategori SOP",
                    required: true,
                    errormessage: this.state.errorKat,
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
                    name: "linkDokumen",
                    errormessage: this.state.errorLink,
                    value: this.state.linkDokumen,
                    placeholder: "https://drive.google.com/drive/my-drive"
                }

            ]
           
            
        )

        
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
                <SirioButton purple
                    classes="mx-1"
                    onClick={() => window.location.href = "/sop"}>
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
                    title="Form Tambah SOP"
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
             </>
        );
    }
}

export default withRouter (FormTambahSOP);