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
            risiko: {}
        }

        this.renderDataRisiko = this.renderDataRisiko.bind(this);
    }

    componentDidMount() {
        this.renderDataRisiko();
        console.log(this.props.location)
    }

    parentFormatter() {
        if (this.state.risiko.parent) {
            return this.state.risiko.parent
        } else {
            return "-"
        }
    }

    async renderDataRisiko() {
        const response = await RegistrasiRisikoService.getRisiko(this.props.location.state.id);

        this.setState({
            risiko: response.data.result
        })
    }

    data() {
        return {
            "Nama Risiko": this.state.risiko.namaRisiko,
            "Kategori Risiko": this.state.risiko.risikoKategori,
            "Referensi SOP": this.state.risiko.sop,
            "Parent": this.parentFormatter(),
            "Komponen Risiko": this.state.risiko.komponen
        };
    }

    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/registrasi-risiko/ubah",
                    state: {
                        id: this.state.risiko.idRisiko,
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