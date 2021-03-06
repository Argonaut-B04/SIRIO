import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDetailPage from '../SirioDetailPage';
import SirioSubdetailPage from '../SirioSubdetailPage';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";
import SirioConfirmButton from "../../Button/ActionButton/SirioConfirmButton";

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
        if (this.state.redirect && this.state.rencanaPemeriksaan.status === 1) {
            return <Redirect to={{
                pathname: "/rencana-pemeriksaan",
                state: {
                    deleteSuccess: true
                }
            }} />
        }else if (this.state.redirect && this.state.rencanaPemeriksaan.status === 2) {
            return <Redirect to={{
                pathname: "/rencana-pemeriksaan",
                state: {
                    endSuccess: true
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
            default:
                return ""
        }
    }

    async renderDataRencana() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);

        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
           rencanaPemeriksaan: response.data.result,
           dataGeneral: {
            "Nama Rencana :": response.data.result.namaRencana,
            "Link Pemeriksaan :": response.data.result.linkMajelis,
            "Status :": this.statusFormatter(response.data.result.status),
            },
            daftarTugasPemeriksaan: response.data.result.daftarTugasPemeriksaan.map(tugas => {
                return (
                    {
                        "Nama QA Officer :": tugas.namaQA,
                        "Kantor Cabang :": tugas.namaKantorCabang,
                        "Tanggal Mulai :": this.getTanggalFormatter(tugas.tanggalMulai),
                        "Tanggal Selesai :": this.getTanggalFormatter(tugas.tanggalSelesai)
                    }
                )
            })
        })

        this.props.contentFinishLoading()
    }

    getTanggalFormatter(tanggalString) {
        if (tanggalString != null ){
            const tahun = tanggalString.split("-")[0]
            const bulan = tanggalString.split("-")[1]
            const tgl = tanggalString.split("-")[2]
            var tglFix = tgl.split(" ")[0]
            var namaBulan = ["Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"];
            return tglFix+ " " + namaBulan[bulan - 1] + " " + tahun;
        }else{
            return "-"
        }
    
        
    }

    hapus(id) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");
        const rencanaPemeriksaan = {
            id: id
        };
       
        RencanaPemeriksaanService.deleteRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());

        this.props.contentFinishLoading()
    }

    async ubahStatus() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");

        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);
        const rencanaPemeriksaan = {
            id: response.data.result.id,
            namaRencana: response.data.result.namaRencana,
            linkMajelis: response.data.result.linkMajelis,
            status: 3,
            daftarTugasPemeriksaan: response.data.result.daftarTugasPemeriksaan
        }
        RencanaPemeriksaanService.editRencanaPemeriksaan(rencanaPemeriksaan)
            .then(() => this.setRedirect());

        this.props.contentFinishLoading()
    }

    subButton(status) {
        if (status === 1){
            return (
                <div>
                    <NavLink to={{
                        pathname: "/rencana-pemeriksaan/ubah",
                        state: {
                            id: this.state.rencanaPemeriksaan.id,
                        }
                    }}>
                        <SirioButton
                            purple recommended
                            classes="mx-2"
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
        }else if (status === 2){
            return (
                <div>
                    <SirioConfirmButton
                        red
                        modalTitle="Konfirmasi Penyelesaian"
                        modalDesc="Apakah anda yakin untuk menyelesaikan rencana pemeriksaan?"
                        onConfirm={() => this.ubahStatus()}
                        customConfirmText="Ya, Selesai"
                        customCancelText="Batal"
                    >
                        Selesai
                    </SirioConfirmButton>
                </div>
            )
        }else{
            return (
                <div>
                   
                </div>
                
            )
        }
    }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioDetailPage
                    title="Detail Rencana Pemeriksaan"
                    data={this.state.dataGeneral}
                    id='id'
                    subButton={this.subButton(this.state.rencanaPemeriksaan.status)}
                    link="rencana-pemeriksaan"
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