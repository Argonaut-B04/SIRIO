import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class DetailRisiko extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            namaRisiko: "",
            risikoKategori: "",
            sop: "",
            parent: "",
            komponen: "",

        }

        this.renderDataRisiko = this.renderDataRisiko.bind(this);
    }

    componentDidMount() {
        this.renderDataRisiko();
        console.log(this.props.location)
    }

    parentFormatter() {
        if (this.state.parent) {
            return this.state.parent.namaRisiko
        } else {
            return "-"
        }
    }

    async renderDataRisiko() {
        const response = await RegistrasiRisikoService.getRisiko(this.props.location.state.id);

        this.setState({
            namaRisiko: response.data.result.namaRisiko,
            risikoKategori: response.data.result.risikoKategori,
            sop: response.data.result.sop.judul,
            parent: response.data.result.parent,
            komponen: response.data.result.komponen,
        })
    }

    data() {
        return {
            "Nama Risiko": this.state.namaRisiko,
            "Kategori Risiko": this.state.risikoKategori,
            "Referensi SOP": this.state.sop,
            "Parent": this.parentFormatter(),
            "Komponen Risiko": this.state.komponen
        };
    }

    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/registrasi-risiko/ubah",
                    state: {
                        id: this.state.idRisiko,
                    }
                }}>
                    <SirioButton
                    classes="mx-2"
                    purple recommended
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
                <SirioConfirmButton
                    purple
                    modalTitle="Apa Anda yakin untuk menghapus risiko?"
                    onConfirm={() => window.location.href = "http://www.google.com"}
                    customConfirmText="Ya, hapus"
                    customCancelText="Batal"
                >
                    Hapus
                </SirioConfirmButton>
            </div>
        )
    }

    render() {
        return (
            <SirioDetailPage
                title="Detail Risiko"
                data={this.data()}
                id='id'
                columnsDefinition={this.columns}
                subButton={this.subButton()}
                link="registrasi-risiko"
            />
        );
    }
} 

export default withRouter(DetailRisiko);