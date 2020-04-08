import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService'
import { withRouter } from 'react-router-dom';

class FormFeedbackBukti extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            feedback: "",
            buktiPelaksanaan: {}
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    // componentDidMount() {
    //     this.renderDataBukti();
    // }

    // async renderDataBukti() {
    //     const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);

    //     this.setState({
    //         buktiPelaksanaan: response.data.result
    //     })
    // }

    // data() {
    //     return {
    //         "Keterangan": this.state.buktiPelaksanaan.keterangan,
    //         "Lampiran": this.state.buktiPelaksanaan.lampiran
    //     }
    // }

    data = {
        Keterangan: "test",
        Lampiran: "test"
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
        event.preventDefault();
        const buktiPelaksanaan = {
            feedback: this.state.feedback
        }
        BuktiPelaksanaanService.submitPersetujuan(this.props.location.state.id, buktiPelaksanaan)
            .then(() => {
                window.location.href = "/bukti-pelaksanaan"
                
            });
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
                label: "Feedback",
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
                    onClick={(event) => this.handleSubmit(event)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                    onClick={() => window.location.href = "/bukti-pelaksanaan/persetujuan"}>
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

export default withRouter(FormFeedbackBukti);