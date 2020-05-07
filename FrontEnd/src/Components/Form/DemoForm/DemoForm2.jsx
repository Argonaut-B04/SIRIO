import React, { Component } from 'react'
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';

// Demo form untuk 1 form dalam halaman, lalu semua field masuk dalam 1 dictionary di state
// misal: "nama" disimpan di this.state.daftarField.nama
// kalau kamu simpan 'nama' di this.state.nama, lihat DemoForm3 
export class DemoForm2 extends Component {

    // pastikan inisiasi ada 2 list, yang pertama adalah daftar value, yang kedua adalah daftar error
    constructor(props) {
        super(props);
        this.state = {
            daftarField: {},
            daftarError: {}
        }

        this.handleChange = this.handleChange.bind(this);
    }

    // ini akan dipanggil ketika isi dari field terganti
    handleChange(event) {

        // tinjau daftar value nya
        const { daftarField } = this.state;

        // kita ganti value nya
        daftarField[event.target.name] = event.target.value;

        // setelah diganti, simpan ke state
        this.setState({
            daftarField: daftarField
        })

        // baru validasi
        this.validate(event);
    }

    // ini fungsi validasi
    validate(event) {

        // ini validasi untuk input dengan name='field'
        if (event.target.name === "field") {

            // sekarang, kita tinjau daftar error nya
            const { daftarError } = this.state;

            // Misalnya, aku bikin dia error kalau value nya adalah "halo"
            if (event.target.value === "halo") {

                // kita ganti isi dari error nya
                daftarError[event.target.name] = "halo juga";
            } else {

                // kalau value nya selain "halo", kita kosongkan isi error nya
                daftarError[event.target.name] = "";
            }

            // lalu kita simpan ke state
            this.setState({
                daftarError: daftarError
            })

        } else if (event.target.name === "secondField") {

            // sekarang, kita tinjau daftar error nya
            const { daftarError } = this.state;

            // Misalnya, aku bikin dia error kalau value nya adalah "halo"
            if (event.target.value === "halo") {

                // kita ganti isi dari error nya
                daftarError[event.target.name] = "halo juga";
            } else {

                // kalau value nya selain "halo", kita kosongkan isi error nya
                daftarError[event.target.name] = "";
            }

            // lalu kita simpan ke state
            this.setState({
                daftarError: daftarError
            })
        }
    }

    // ini input definition, kita definisikan isi dari tiap field
    getInputDefinitions() {

        // Yang perlu kita pake dari state adalah daftar value dan daftar error nya
        const { daftarField, daftarError } = this.state;

        return (
            [
                {
                    label: "Field 1",
                    type: "textarea",

                    // perlu diperhatikan kita pass value dan errormessage spesifik
                    // sangat disarankan struktur data nya sama, supaya tidak bingung
                    value: daftarField.field,
                    errormessage: daftarError.field,

                    // name ini sangat penting, sesuaikan dengan paling kanan di value
                    name: "field",

                    // Kita kirim handleChange nya
                    handleChange: this.handleChange,
                },
                {
                    label: "Field 2",
                    type: "textarea",

                    // Beda kan? paling kanan nya adalah spesifik
                    value: daftarField.secondField,
                    errormessage: daftarError.secondField,

                    // Sekali lagi, name itu harus sama dengan paling kanan di value
                    name: "secondField",

                    handleChange: this.handleChange,
                }
            ]
        )
    }

    render() {
        return (
            <>
                <SirioForm
                    title="testForm2"
                    inputDefinition={this.getInputDefinitions()}
                    submitButton={<SirioButton purple hover> Submit </SirioButton>}
                />
            </>
        )
    }
}

export default DemoForm2
