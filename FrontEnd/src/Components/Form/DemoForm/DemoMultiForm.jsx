import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';

/**
 * Kelas untuk membuat form demo
 */
export default class DemoMultiForm extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            formList: [
                {
                    nama: "bambang",
                    umur: 18,
                    jenisKelamin: "Pria",
                }
            ]
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
    }

    handleChange(event, index) {
        const formList = this.state.formList
        formList[index][event.target.name] = event.target.value;

        this.setState(
            {
                formList: formList
            }
        )
    }

    handleSelectChange(name, target, index) {
        const formList = this.state.formList;
        formList[index][name] = target.value;

        this.setState(
            {
                formList: formList
            }
        )
    }

    handleSubmit(event) {
        alert("submited");
        event.preventDefault();
    }

    inputDefinition(index) {
        return (
            [
                {
                    label: "Nama",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "nama",
                    index: index,
                    value: this.state.formList[index].nama,
                    placeholder: "masukan nama"
                }, {
                    label: "Umur",
                    handleChange: this.handleChange,
                    type: "number",
                    name: "umur",
                    index: index,
                    value: this.state.formList[index].umur,
                    placeholder: "masukan umur"
                }, {
                    label: "Jenis Kelamin",
                    handleChange: this.handleSelectChange,
                    index: index,
                    type: "select",
                    name: "jenisKelamin",
                    value: this.state.formList[index].jenisKelamin,
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

    deleteItem(array, index) {
        const toReturn = []
        for (var row in array) {
            // eslint-disable-next-line
            if (row != index) {
                toReturn.push(array[row]);
            }
        }
        return toReturn;
    }


    deleteChildForm(index) {
        const formList = this.state.formList
        const newFormList = this.deleteItem(formList, index)

        this.setState(
            {
                formList: newFormList
            }
        )
    }

    childFooterButton(index) {
        return (
            <div>
                <SirioButton
                    red
                    type="button"
                    onClick={() => this.deleteChildForm(index)}
                >
                    Hapus
                </SirioButton>
            </div>
        )
    }

    addForm() {
        const formList = this.state.formList
        formList.push({});

        this.setState(
            {
                formList: formList
            }
        )
    }

    // Fungsi render SirioForm
    render() {
        const forms = [];
        for (let i = 1; i < this.state.formList.length; i++) {
            forms.push(
                <SirioForm
                    key={i}
                    childForm
                    id={i}
                    inputDefinition={this.inputDefinition(i)}
                    footerButton={this.childFooterButton(i)}
                />
            )
        }
        return (
            <>
                <SirioForm
                    id={0}
                    title="Demo Multi Form"
                    subtitle="Ini demo form untuk ngedemoin Multi Form"
                    inputDefinition={this.inputDefinition(0)}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
                {forms.map(form => form)}
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