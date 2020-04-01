import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';

export default class FormFeedbackBukti extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            feedback: ""
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    data = {
        Keterangan: "Keterangan bukti rekomendasi",
        Lampiran: "https://drive.google.com/drive/folders/1SvB_2W4BjD8rxVQR1-dDbA63-4Zx-hoN"
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    handleSubmit(event) {
        alert("submited");
        event.preventDefault();
    }

    inputDefinition() {
        var rowDefinition = [];
        Object.keys(this.data).map(key => rowDefinition.push(
            {
                label: key,
                customInput: <p>{this.data[key]}</p>
            }
        ))
        
        rowDefinition.push(
            {
                label: "Feedback :",
                handleChange: this.handleChange,
                type: "textarea",
                name: "feedback",
                value: this.state.feedback,
                placeholder: "Masukan feedback penolakan"
            }
        )
        
        return rowDefinition;
    }

    submitButton() {
        return (
            <div>
                <SirioButton purple recommended
                    classes="mx-2"
                    onClick={() => window.location.href = "/bukti-pelaksanaan/detail-persetujuan"}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/bukti-pelaksanaan/detail-persetujuan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    render() {
        return (
            <SirioForm
                title="Form Feedback Penolakan Bukti"
                data={this.data}
                id='id'
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
        );
    }
} 