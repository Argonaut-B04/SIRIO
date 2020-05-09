import React, { Component } from 'react'
import SirioForm from '../SirioForm';
import SirioComponentHeader from '../../Header/SirioComponentHeader';
import ComponentWrapper from '../../../Layout/ComponentWrapper';
import SirioButton from '../../Button/SirioButton';

// Demo form untuk validasi multiple form dalam 1 page
export class DemoForm extends Component {

    // pastikan inisiasi ada 2 list, yang pertama adalah daftar value, yang kedua adalah daftar error
    constructor(props) {
        super(props);
        this.state = {
            daftarForm: [],
            daftarError: []
        }

        this.handleInsideFieldChange = this.handleInsideFieldChange.bind(this);
    }

    // ini untuk simulasi, misalnya ada 5 form
    getForm() {
        var forms = []
        for (let i = 0; i < 5; i++) {
            forms.push(
                <SirioForm
                    key={i}
                    noHeader
                    isInnerForm
                    inputDefinition={this.getInputDefinitions(i)}
                />
            )
        }
        return forms;
    }

    // ini akan dipanggil ketika isi dari field terganti
    handleInsideFieldChange(event, index) {

        // tinjau daftar value nya
        const { daftarForm } = this.state;

        // kita ganti value nya
        daftarForm[index].daftarField[event.target.name] = event.target.value;

        // setelah diganti, simpan ke state
        this.setState({
            daftarForm: daftarForm
        })

        // baru validasi
        this.validate(event, index);
    }

    // ini fungsi validasi, perlu diperhatikan ini untuk multiple form
    validate(event, index) {

        // sekarang, kita tinjau daftar error nya
        const { daftarError } = this.state;

        // Misalnya, aku bikin dia error kalau value nya adalah "halo"
        if (event.target.value === "halo") {

            // kita ganti isi dari error nya
            daftarError[index].daftarField[event.target.name] = "halo juga";
        } else {

            // kalau value nya selain "halo", kita kosongkan isi error nya
            daftarError[index].daftarField[event.target.name] = "";
        }

        // lalu kita simpan ke state
        this.setState({
            daftarError: daftarError
        })
    }

    // Ini untuk input definition dari masing - masing form
    // terinspirasi dari cloud, jadi setiap SirioForm akan punya input definition berbeda
    // sesuai dengan index nya
    getInputDefinitions(index) {

        // Yang perlu kita pake dari state adalah daftar value dan daftar error nya
        const { daftarForm, daftarError } = this.state;

        // Karena ini simulasi, daftar value nya kosong
        // kemungkinan di kalian, daftar value nya tidak akan kosong karena ambil dari backend
        // tapi kalau ada error karena undefined, masukin ini
        if (typeof daftarForm[index] === "undefined") {
            daftarForm[index] = {};
            daftarForm[index].daftarField = {}
        }

        // Ini untuk inisiasi daftar error spesifik untuk field tertentu
        // Struktur data yang aku pake disini adalah dictionary, jadi pake key dan value
        // Yang Cloud implementasi sekarang itu full array
        // Kenapa pake dictionary? karena penamaannya jadi lebih gampang dan bisa dibaca
        if (typeof daftarError[index] === "undefined") {
            daftarError[index] = {};
            daftarError[index].daftarField = {}
        }


        return (
            [
                {
                    label: "Field 1 for " + (index + 1),
                    type: "textarea",

                    // perlu diperhatikan kita pass value dan errormessage spesifik
                    // sangat disarankan struktur data nya sama, supaya tidak bingung
                    value: daftarForm[index].daftarField.field,
                    errormessage: daftarError[index].daftarField.field,

                    // name ini sangat penting, sesuaikan dengan paling kanan di value
                    name: "field",

                    // Tentu saja masukin index, index akan menjadi parameter kedua di fungsi handleChange
                    index: index,

                    // Kita kirim handleChange nya
                    handleChange: this.handleInsideFieldChange,
                },
                {
                    label: "Field 2 for " + (index + 1),
                    type: "textarea",

                    // Beda kan? paling kanan nya adalah spesifik
                    value: daftarForm[index].daftarField.secondField,
                    errormessage: daftarError[index].daftarField.secondField,

                    // Sekali lagi, name itu harus sama dengan paling kanan di value
                    name: "secondField",

                    // kirim index juga
                    index: index,
                    handleChange: this.handleInsideFieldChange,
                }
            ]
        )
    }

    render() {
        return (
            <>
                <SirioComponentHeader
                    title="testForm"
                />

                {/* Kalau form nya ada banyak dan tidak ada field di luar form, tolong jangan pake inner dan outer form */}
                {/* Akan memperberat performa server karena dia harus ngerender dan jalanin semua fungsi form padahal gak ada field nya */}
                {/* Alternatif nya, decompose jadi SirioComponentHeader untuk judul */}
                <ComponentWrapper>

                    {/* lalu pake map, getForm() ini akan mengembalikan list yang isinya 5 SirioForm */}
                    {this.getForm().map(e => e)}

                    {/* Terakhir, tombol submit di sebelah kanan bawah */}
                    <div className="w-100 text-right">
                        <SirioButton purple recommended> Submit </SirioButton>
                    </div>
                </ComponentWrapper>
            </>
        )
    }
}

export default DemoForm
