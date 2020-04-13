import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDetailPage from '../SirioDetailPage';
import SirioSubdetailPage from '../SirioSubdetailPage';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";

class DetailRencanaPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rencanaPemeriksaan: {},
            daftarTugasPemeriksaan: [],
            dataGeneral: {},
            redirect: false
        }

        this.renderDataRencana = this.renderDataRencana.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderDataRencana();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/manager/rencanaPemeriksaan",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    statusFormatter(status) {
        switch (status) {
            case 1:
                return (
                    <span style={{ color: '#black' }}>Draft</span>
                );
            case 3:
                return (
                    <span style={{ color: '#5DBCD2' }}>Selesai</span>
                );
            case 2:
                return (
                    <span style={{ color: '#6FCF97' }}>Sedang Dijalankan</span>
                );
        }
    }

    async renderDataRencana() {
        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);

        this.setState({
           rencanaPemeriksaan: response.data.result,
           dataGeneral: {
            "Nama Rencana      :": response.data.result.namaRencana,
            "Link Pemeriksaan  :": response.data.result.linkMajelis,
            "Status            :": this.statusFormatter(response.data.result.status),
            },
            daftarTugasPemeriksaan: response.data.result.daftarTugasPemeriksaan.map(tugas => {
                return (
                    {
                        "Nama QA": tugas.namaQA,
                        "Kantor Cabang": tugas.namaKantorCabang,
                        "tanggalMulai": tugas.tanggalMulai,
                        "tanggalSelesai": tugas.tanggalSelesai
                    }
                )
            })
        })
    }

    hapus(id) {
        const rencanaPemeriksaan = {
            id: id
        };
        RencanaPemeriksaanService.deleteRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());
    }

    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/manager/rencanaPemeriksaan/ubah",
                    state: {
                        id: this.state.rencanaPemeriksaan.id,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
                <SirioWarningButton
                    red
                    modalTitle="Konfirmasi Penghapusan"
                    modalDesc="Apakah anda yakin untuk menghapus rencana pemeriksaan?"
                    onConfirm={() => this.hapus(this.state.rencanaPemeriksaan.id)}
                    customConfirmText="Ya, Hapus"
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
                    title="Detail Rencana Pemeriksaan"
                    data={this.state.dataGeneral}
                    id='id'
                    subButton={this.subButton()}
                    link="manager/rencanaPemeriksaan"
                />
                {this.state.daftarTugasPemeriksaan.map(tugas=>
                    <SirioSubdetailPage
                        title="Tugas Pemeriksaan"
                        data={tugas}
                        id='id'
                    />
                )}
            </>
            
        );
    }
} 

export default withRouter(DetailRencanaPemeriksaan);