import React, { Component } from 'react';
import SirioForm from '../Components/Form/SirioForm';

class TheForm extends Component {

    constructor(props) {
        super(props);

        this.state = {
            nama: "bambang",
            umur: 18
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

    handleSubmit(event) {
        alert("submited");
        event.preventDefault();
    }

    inputDefinition() {
        return (
            [
                {
                    label: "Nama Bambang",
                    handleChange: this.handleChange,
                    type: "text",
                    name: "nama",
                    value: this.state.nama,
                    placeholder: "masukan nama bambang"
                }, {
                    label: "Umur Bambang",
                    handleChange: this.handleChange,
                    type: "number",
                    name: "umur",
                    value: this.state.umur,
                    placeholder: "masukan umur bambang"
                }
            ]
        )
    }

    render() {
        return (
            <SirioForm
                title="Demo Form"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
            />
        );
    }
}

export default TheForm;
