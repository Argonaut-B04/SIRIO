import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService'

export default class FormBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            keterangan: "",
            lampiran: ""
           
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
    }

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
        event.preventDefault();
        const buktiPelaksanaan = {
            keterangan: this.state.keterangan,
            lampiran: this.state.lampiran
            
        }
        BuktiPelaksanaanService.addBuktiPelaksanaan(buktiPelaksanaan)
        .then( (response) => console.log(response) );
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
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
                    onClick={(event)  => this.handleSubmit(event)}>
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
                title="Form Tambah Bukti Pelaksanaan Rekomendasi"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
}