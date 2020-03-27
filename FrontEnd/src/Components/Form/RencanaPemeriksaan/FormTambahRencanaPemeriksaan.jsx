import React from 'react';
import SirioForm from '../SirioForm';

/**
 * Kelas untuk membuat form demo
 */
export default class FormTambahKantorCabang extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "Kantor Cabang 1",
            mulai: 12/12/2020,
            selesai: 19/12/2020,
            qa: "Billa"
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
                    label: "Kantor Cabang*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "Masukan nama point"
                }, {
                    label: "QA Officer*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "bm",
                    value: this.state.qa,
                    placeholder: "Masukan nama QA Officer"
                },{
                    label: "Tanggal Mulai*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "tanggalMulai",
                    value: this.state.mulai,
                    placeholder: "Masukan tanggal mulai"
                },{
                    label: "Tanggal Selesai*",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "tanggalSelesai",
                    value: this.state.selesai,
                    placeholder: "Masukan tanggal mulai"
                }

            ]
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Form Tambah Rencana Pemeriksaan"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
            />
        );
    }
}