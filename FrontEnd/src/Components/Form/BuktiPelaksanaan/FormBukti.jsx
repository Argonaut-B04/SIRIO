import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService'
import { withRouter } from 'react-router-dom';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';

class FormBukti extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            keterangan: "",
            lampiran: "",
            id: "",
            addComplete: false
        }
        // this.renderId = this.renderId.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.inputDefinition = this.inputDefinition.bind(this);
    }

    // componentDidMount() {
    //     console.log(this.props.location.state.id);
    // }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    endNotification() {
        this.setState({
            addComplete: false
        })
    }

    handleSubmit(event) {
        event.preventDefault();
        const buktiPelaksanaan = {
            keterangan: this.state.keterangan,
            lampiran: this.state.lampiran
        }
        BuktiPelaksanaanService.submitChanges(this.props.location.state.id, buktiPelaksanaan)
            .then(() => {
                window.location.href = "/bm/rekomendasi"
                this.setState({
                    addComplete: true
                })
            });
    }

    // async renderId() {
    //     const response = this.props.location.state.id;
    //     this.setState({
    //         id: response
    //     })
    // }

    // Fungsi yang akan mengembalikan definisi tiap field pada form
    inputDefinition() {
        return (
            [
                {
                    label: "Keterangan*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "keterangan",
                    value: this.state.keterangan,
                    placeholder: "Masukan keterangan bukti"
                }, {
                    label: "Lampiran*",
                    handleChange: this.handleChange,
                    type: "textarea",
                    name: "lampiran",
                    value: this.state.lampiran,
                    placeholder: "Masukan lampiran bukti"
                }
            ]
        )
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
                    onClick={() => window.location.href = "/bm/rekomendasi"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <div>
            <SirioForm
                title="Form Tambah Bukti Pelaksanaan Rekomendasi"
                inputDefinition={this.inputDefinition()}
                onSubmit={this.handleSubmit}
                submitButton={this.submitButton()}
            />
            {this.state.addComplete &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Bukti pelaksanaan berhasil ditambahkan"
                    customConfirmText="Kembali"
                    onClick={this.endNotification}
                />
            }
            </div>
        );
    }
}

export default withRouter(FormBukti);