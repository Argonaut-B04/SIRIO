import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';

export default class FormBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            keterangan: "",
            lampiran: "",
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
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
                    label: "Keterangan*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "keterangan",
                    value: this.state.keterangan,
                    placeholder: "Masukan keterangan bukti"
                }, {
                    label: "Lampiran*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "lampiran",
                    value: this.state.lampiran,
                    placeholder: "Masukan lampiran bukti"
                }
            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={() => window.location.href = "/bm/rekomendasi"}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/bm/rekomendasi"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Form Pengajuan Bukti Pelaksanaan Rekomendasi"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
}