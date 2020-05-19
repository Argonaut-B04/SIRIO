import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
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
            detailUraian: "",
            ketentuanSampel: "",
            metodologi: "",
            deskripsi: "",
            child: [],
            redirect: false,

        }

        this.renderDataRisiko = this.renderDataRisiko.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderDataRisiko();
    }

    detailUraianFormatter() {
        if (this.state.detailUraian === "" | this.state.detailUraian === null) {
            return "-"
        } else {
            return this.state.detailUraian
        }
    }

    metodologiFormatter() {
        if (this.state.metodologi === "" | this.state.metodologi === null) {
            return "-"
        } else {
            return this.state.metodologi
        }
    }

    deskripsiFormatter() {
        if (this.state.deskripsi === "" | this.state.deskripsi === null) {
            return "-"
        } else {
            return this.state.deskripsi
        }
    }

    ketentuanSampelFormatter() {
        if (this.state.ketentuanSampel === "" | this.state.ketentuanSampel === null) {
            return "-"
        } else {
            return this.state.ketentuanSampel
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
            detailUraian: response.data.result.detailUraian,
            metodologi: response.data.result.metodologi,
            ketentuanSampel: response.data.result.ketentuanSampel,
            deskripsi: response.data.result.deskripsi
            // child: response.data.result.childList,
        })
    }

    data() {
        return {
            "Nama Risiko": this.state.namaRisiko,
            "Kategori Risiko": this.state.risikoKategori,
            "Referensi SOP": this.state.judulSop,
            "Detail Uraian Risiko": this.detailUraianFormatter(),
            "Metodologi": this.metodologiFormatter(),
            "Deskripsi": this.deskripsiFormatter(),
            "Ketentuan Sampel": this.ketentuanSampelFormatter()
        };
    }

    // childFormatter() {
    //     if (this.state.risikoKategori === 3 | this.state.risikoKategori === null | this.state.child.length === 0) {
    //         return "-"
    //     } else {
    //         var list = [];
    //         for (let i = 0; i < this.state.child.length; i++) {
    //             const nama = this.state.child[i].namaRisiko;
    //             list.push(nama);
    //         }
    //         return ( 
    //         <> 
    //         {list.map(child => 
    //         <li>
    //             {child}
    //         </li>
    //         )} 
    //         </> 
    //         )
    //     }
    // }

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
                <SirioWarningButton
                    purple
                    modalTitle="Apa Anda yakin untuk menghapus risiko?"
                    onConfirm={() => this.hapus(this.state.idRisiko)}
                    customConfirmText="Ya, hapus"
                    customCancelText="Batal"
                >
                    Hapus
                </SirioWarningButton>
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
                subButton={this.subButton()}
            />
            </>
        );
    }
} 

export default withRouter(DetailRisiko);