import React from 'react';
import SirioForm from '../../Form/SirioForm';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "Kantor Cabang 1",
            umur: 18
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
    }

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

    // Fungsi yang akan dijalankan ketika user submit
    // Umumnya akan digunakan untuk memanggil service komunikasi ke backend
    handleSubmit(event) {
        alert("submited");
        // event.preventDefault wajib ada
        event.preventDefault();
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    inputDefinition() {
        return (
            [
                {
                    label: "Nama Point",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Masukan nama point"
                }, {
                    label: "Branch Manger",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "bm",
                    value: this.state.umur,
                    placeholder: "masukan umur bambang"
                },{
                    label: "Area",
                    handleChange: this.handleChange,
                    type: "number",
                    name: "umur",
                    value: this.state.umur,
                    placeholder: "masukan umur bambang"
                },{
                    label: "Regional",
                    handleChange: this.handleChange,
                    type: "number",
                    name: "umur",
                    value: this.state.umur,
                    placeholder: "masukan umur bambang"
                }

            ]
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Form Tambah Kantor Cabang"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
            />
        );
    }
}