import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import SirioField from '../SirioFormComponent/SirioField';

/**
 * Kelas untuk membuat form demo
 */
export default class DemoForm extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            nama: "bambang",
            umur: 18,
            jenisKelamin: "Pria",
            manusia: true,
            customDropdown: "bambang",
            customDropdown2: "pria",
            totalForm: 1
        }

        this.handleChange = this.handleChange.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.addForm = this.addForm.bind(this);
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        console.log(event);
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
        alert("submited");
        // event.preventDefault wajib ada
        event.preventDefault();
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    innerInputDefinition() {
        return (
            [
                {
                    label: "Nama",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "masukan nama"
                }, {
                    label: "Umur",
                    handleChange: this.handleChange,
                    type: "number",
                    name: "umur",
                    value: this.state.umur,
                    placeholder: "masukan umur"
                }, {
                    label: "Jenis Kelamin",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "jenisKelamin",
                    value: this.state.jenisKelamin,
                    optionList: [
                        {
                            label: "Pria",
                            value: "Pria"
                        }, {
                            label: "Wanita",
                            value: "Wanita"
                        }, {
                            label: "Gak tau apa",
                            value: "gak tau apa"
                        }
                    ]
                }
            ]
        )
    }

    outerInputDefinition() {
        return (
            [
                {
                    fullComponent:
                        <SirioForm
                            noHeader
                            isInnerForm
                            inputDefinition={this.innerInputDefinition()}
                        />
                }, {
                    label: "Manusia?",
                    handleChange: this.handleChange,
                    type: "checkbox",
                    name: "manusia",
                    value: this.state.manusia,
                }
            ]
        )
    }

    customSelectHandle(name, event) {
        if (event.value === "vanish") {
            this.setState({
                nama: "Solo Quisiera Desaparecer"
            })
        } else if (event.value === "bambang") {
            this.setState({
                nama: "Bambang"
            })
        }
    }

    getBetween() {
        return (
            <>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={(name, event) => {
                            this.handleSelectChange(name, event);
                            this.customSelectHandle(name, event);
                        }}
                        classes="p-1"
                        name="customDropdown"
                        value={this.state.customDropdown}
                        optionList={
                            [
                                {
                                    label: "Bambang",
                                    value: "bambang"
                                },
                                {
                                    label: "Vanish",
                                    value: "vanish"
                                }
                            ]
                        }
                    />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="customDropdown2"
                        value={this.state.customDropdown2}
                        optionList={
                            [
                                {
                                    label: "Chisato",
                                    value: 1
                                },
                                {
                                    label: "Chidua",
                                    value: 2
                                }
                            ]
                        }
                    />
                </div>
            </>
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                >
                    Simpan
                </SirioButton>
                <SirioButton purple
                    classes="ml-2"
                    type="button"
                    onClick={() => alert("batal")}
                >
                    Batal
                </SirioButton>
            </div>
        )
    }

    addForm() {
        // this.setState({
        //     totalForm: ++this.state.totalForm
        // })
    }

    // Fungsi render SirioForm
    render() {
        var forms = [];
        for (let i = 1; i < this.state.totalForm; i++) {
            forms.push(
                <SirioForm
                    noHeader
                    inputDefinition={this.innerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            )
        }
        return (
            <>
                <SirioForm
                    title="Demo Form"
                    betweenTitleSubtitle={this.getBetween()}
                    subtitle="Ini demo form untuk ngedemoin ... form"
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
                {forms.map(object => {
                    return object;
                })}
                <div className="w-100 text-right">
                    <SirioButton blue recommended
                        classes="mr-3"
                        onClick={() => this.addForm()}
                    >
                        Tambah Form
                    </SirioButton>
                </div>
            </>
        );
    }
}