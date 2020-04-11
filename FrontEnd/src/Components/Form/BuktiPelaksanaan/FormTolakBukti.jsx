import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormTolakBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            buktiPelaksanaan: {},
            feedback: "",
            redirect: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.renderDataBuktiPelaksanaan = this.renderDataBuktiPelaksanaan.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderDataBuktiPelaksanaan();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/bukti-pelaksanaan",
                state: {
                    rejectSuccess: true
                }
            }} />
        }
    };

    async renderDataBuktiPelaksanaan() {
        const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);
        
        this.setState({
            id: response.data.result.id,
            keterangan: response.data.result.keterangan,
            lampiran: response.data.result.lampiran
        })
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
            status: 3,
            feedback: this.state.feedback
        };
        BuktiPelaksanaanService.setStatusBukti(this.state.id, buktiPelaksanaan)
            .then(() => this.setRedirect());
    }

    inputDefinition() {
        var rowDefinition = [];
        // Object.keys(this.data).map(key => rowDefinition.push(
        //     {
        //         label: key,
        //         customInput: <p>{this.data[key]}</p>
        //     }
        // ))
        rowDefinition.push(
            {
                label: "Keterangan",
                customInput: <p>{this.state.keterangan}</p>
            }, {
                label: "Lampiran",
                customInput: <p>{this.state.lampiran}</p>
            }, {
                label: "Feedback*",
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
                <SirioConfirmButton
                    purple recommended
                    classes="mx-1"
                    modalTitle="Apakah anda yakin untuk menolak bukti pelaksanaan?"
                    onConfirm={(event)  => this.handleSubmit(event)}
                    customConfirmText="Ya, Tolak"
                    customCancelText="Batal"
                >
                    Simpan
                </SirioConfirmButton>
                <SirioButton purple
                             classes="mx-1"
                             onClick={() => window.location.href = "/bukti-pelaksanaan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Feedback Penolakan Bukti Pelaksanaan"
                    data={this.data}
                    id='id'
                    inputDefinition={this.inputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}
export default withRouter(FormTolakBukti);