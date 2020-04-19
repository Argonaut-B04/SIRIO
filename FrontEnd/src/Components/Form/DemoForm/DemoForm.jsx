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
            nama: "",
            umur: "",
            jenis: "",
            manusia: true,
            customDropdown: "",
            customDropdown2: "",
            namaMain: ["Chocola", "Vanilla"],
            submitable: true,
            submitableArray: [false, false],
        }

        this.handleChange = this.handleChange.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleMultiFieldChange = this.handleMultiFieldChange.bind(this);
        this.modifyFieldCount = this.modifyFieldCount.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateNama = this.validateNama.bind(this);
        this.validateUmur = this.validateUmur.bind(this);
        this.submitable = this.submitable.bind(this);
    }

    // Fungsi untuk mengubah state ketika isi dari input diubah
    // Fungsi ini wajib ada jika membuat form
    handleChange(event) {
        if (event.target.type === "checkbox") {
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
        event.preventDefault();
        if (this.submitable()) {
            alert("submited");
        }
    }

    submitable() {
        var submitable = true;
        const fokus = this.state.submitableArray;
        for (let i = 0; i < fokus.length; i++) {
            submitable = submitable && fokus[i]
        }
        return submitable;
    }

    validateNama(fokusNama) {
        var submitable = true;
        var errorNama;
        if (fokusNama.length < 2) {
            submitable = false;
            errorNama = "terlalu pendek ?";
        } else if (fokusNama.length > 10) {
            submitable = false;
            errorNama = "terlalu panjaaaaaaaa aaaaaaaaaa aaaaaa aaaaaaaaang ?";
        }

        if (submitable !== this.state.submitableArray[0]) {
            var stateToChange = this.state.submitableArray;
            stateToChange[0] = submitable;
            this.setState({
                submitableArray: stateToChange
            })
        }
        return errorNama;
    }

    validateUmur(fokusUmur) {
        var submitable = true;
        var errorUmur;
        if (fokusUmur < 18) {
            submitable = false;
            errorUmur = "Khusus 18 tahun keatas ya ^-^";
        }
        
        if (submitable !== this.state.submitableArray[1]) {
            var stateToChange = this.state.submitableArray;
            stateToChange[1] = submitable;
            this.setState({
                submitableArray: stateToChange
            })
        }
        return errorUmur;
    }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    // Setiap objek {} pada List [] akan menjadi 1 field
    // untuk informasi lebih lengkap, cek SirioForm
    innerInputDefinition() {
        return (
            [
                {
                    label: "Nama",
                    required: true,
                    handleChange: this.handleChange,
                    validationFunction: this.validateNama,
                    type: "textarea",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "masukan nama",
                }, {
                    label: "Umur",
                    handleChange: this.handleChange,
                    validationFunction: this.validateUmur,
                    type: "number",
                    name: "umur",
                    min: 1,
                    value: this.state.umur,
                    placeholder: "masukan umur",
                    required: true
                }, {
                    label: "Jenis",
                    handleChange: this.handleSelectChange,
                    type: "select",
                    name: "jenis",
                    value: this.state.jenis,
                    required: true,
                    optionList: [
                        {
                            label: "Pria",
                            value: "Pria"
                        }, {
                            label: "Wanita",
                            value: "Wanita"
                        }, {
                            label: "Kucing",
                            value: "neko"
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
                    label: "Nama Mereka",
                    multiple: true,
                    handleChange: this.handleMultiFieldChange,
                    type: "text",
                    name: "namaMain",
                    value: this.state.namaMain,
                    modifier: (name, newArray) => this.modifyFieldCount(name, newArray)
                }
            ]
        )
    }

    modifyFieldCount(name, newField) {
        this.setState(
            {
                [name]: newField
            }
        )
    }

    handleMultiFieldChange(event, index) {
        const targetName = event.target.name;
        const targetValue = event.target.value;
        const targetArray = this.state[targetName];
        targetArray[index] = targetValue;
        this.setState(
            {
                [event.target.name]
                    : targetArray
            }
        )
    }

    customSelectHandle(name, event) {
        if (event.value === "vanish") {
            this.setState({
                nama: "Solo Quisiera Desaparecer"
            })
        } else if (event.value === "Shironeko") {
            this.setState({
                nama: "Shironeko"
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
                                    label: "Shironeko",
                                    value: "Shironeko"
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
        var tombolSimpan =
            <SirioButton
                purple
                disabled
            >
                Simpan
            </SirioButton>
        if (this.submitable()) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                >
                    Simpan
            </SirioButton>
        }
        return (
            <div>
                {tombolSimpan}
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
    // Fungsi render SirioForm
    render() {
        return (
            <SirioForm
                title="Demo Form"
                betweenTitleSubtitle={this.getBetween()}
                subtitle="Ini demo form untuk ngedemoin ... form"
                inputDefinition={this.outerInputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
}