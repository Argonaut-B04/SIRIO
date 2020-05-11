import React, { Component } from 'react'
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';

// Demo form untuk
export class DemoForm3 extends Component {

    // pastikan inisiasi ada 2 list, yang pertama adalah daftar value, yang kedua adalah daftar error
    constructor(props) {
        super(props);
        this.state = {}

        this.handleChange = this.handleChange.bind(this);
    }

    // ini akan dipanggil ketika isi dari field terganti
    handleChange(event) {

        // simpan ke state
        this.setState({
            [event.target.name]: event.target.value
        })

        // baru validasi
        this.validate(event);
    }

    // ini fungsi validasi
    validate(event) {

        // ini untuk nampung isi error nya apa
        var hasilError;

        // misalnya ini adalah validasi untuk input dengan name: "field"
        if (event.target.name === "field") {

            // Misalnya, aku bikin dia error kalau value nya adalah "halo"
            if (event.target.value === "halo") {

                // kita ganti isi dari error nya
                hasilError = "halo juga";
            } else {

                // kalau value nya selain "halo", kita kosongkan isi error nya
                hasilError = "";
            }

            // lalu kita simpan ke state, misal disini aku kasi nama error nya "errorField1"
            this.setState({
                errorField1: hasilError
            })

        } else if (event.target.name === "secondField") {

            // Misalnya, aku bikin dia error kalau value nya adalah "halo"
            if (event.target.value === "halo") {

                // kita ganti isi dari error nya
                hasilError = "halo juga";
            } else {

                // kalau value nya selain "halo", kita kosongkan isi error nya
                hasilError = "";
            }

            // lalu kita simpan ke state, misal disini aku kasi nama error nya "errorField2"
            this.setState({
                errorField2: hasilError
            })

        }
    }

    // ini input definition, kita definisikan isi dari tiap field
    getInputDefinitions() {

        // kita akan menggunakan variabel field dan secondField dari state
        const { field, secondField, errorField1, errorField2 } = this.state;

        return (
            [
                {
                    label: "Field 1",
                    type: "textarea",

                    // perlu diperhatikan kita pass value dan errormessage spesifik
                    // sangat disarankan struktur data nya sama, supaya tidak bingung
                    value: field,
                    errormessage: errorField1,

                    // name ini sangat penting, sesuaikan dengan nama state nya
                    name: "field",

                    // Kita kirim handleChange nya
                    handleChange: this.handleChange,
                },
                {
                    label: "Field 2",
                    type: "textarea",

                    // Beda kan? yang penting spesifik
                    value: secondField,
                    errormessage: errorField2,

                    // Sekali lagi, name itu harus sama dengan nama state nya
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
                    title="testForm3"
                    inputDefinition={this.getInputDefinitions()}
                    submitButton={<SirioButton purple hover> Submit </SirioButton>}
                />
            </>
        )
    }
}

export default DemoForm3
