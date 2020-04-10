import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import RegistrasiRisikoService from '../../../Services/RegistrasiRisikoService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';

class DetailRisiko extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            idRisiko: "",
            namaRisiko: "",
            risikoKategori: "",
            sop: "",
            judulSop: "",
            parent: "",
            komponen: "",
            redirect: false,

        }

        this.renderDataRisiko = this.renderDataRisiko.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
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
            idRisiko: response.data.result.idRisiko,
            namaRisiko: response.data.result.namaRisiko,
            risikoKategori: response.data.result.risikoKategori,
            judulSop: response.data.result.sop.judul,
            sop: response.data.result.sop.idSop,
            parent: response.data.result.parent,
            komponen: response.data.result.komponen,
        })
    }

    data() {
        return {
            "Nama Risiko": this.state.namaRisiko,
            "Kategori Risiko": this.state.risikoKategori,
            "Referensi SOP": this.state.judulSop,
            "Parent": this.parentFormatter(),
            "Komponen Risiko": this.state.komponen
        };
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/registrasi-risiko",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    hapus(id) {
        const risiko = {
            id: id
        };
        RegistrasiRisikoService.hapusRisiko(risiko)
            .then(() => this.setRedirect());
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
                    onConfirm={() => this.hapus(this.state.idRisiko)}
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
            <>
            {this.renderRedirect()}
            <SirioDetailPage
                title="Detail Risiko"
                data={this.data()}
                id= 'id'
                columnsDefinition={this.columns}
                subButton={this.subButton()}
                link="registrasi-risiko"
            />
            </>
        );
    }
} 

export default withRouter(DetailRisiko);