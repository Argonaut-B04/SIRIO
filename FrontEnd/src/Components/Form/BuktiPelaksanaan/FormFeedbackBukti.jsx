import React from 'react';
import SirioFormWithDetail from '../SirioFormWithDetail';
import SirioButton from '../../Button/SirioButton';
import classes from '../../DetailPages/BuktiPelaksanaan/DetailBukti.module.css';

export default class FormFeedbackBukti extends React.Component {

    columns = [{
        dataField: 'informasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "5%", textAlign: 'left' };
        }

    }, {
        dataField: 'isiInformasi',
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }];

    data = [
        { "informasi": "Keterangan :", "isiInformasi": "Keterangan bukti rekomendasi tesss" },
        { "informasi": "Lampiran :", "isiInformasi": "https://drive.google.com/drive/folders/1SvB_2W4BjD8rxVQR1-dDbA63-4Zx-hoN" },
    ]

    // Form Feedback
    constructor(props) {
        super(props);

        this.state = {
            feedback: ""
        }

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
    }

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

    handleSubmit(event) {
        alert("submited");
        event.preventDefault();
    }

    inputDefinition() {
        return (
            [
                {
                    label: "Feedback :",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "feedback",
                    value: this.state.feedback,
                    placeholder: "Masukan feedback penolakan"
                }
            ]
        )
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={() => window.location.href = "http://www.google.com"}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "http://www.google.com"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    render() {
        return (
            <SirioFormWithDetail
                title="Form Feedback Penolakan Bukti"
                data={this.data}
                id='id'
                columnsDefinition={this.columns}
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
} 