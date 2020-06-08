import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";
import AuthenticationService from "../../../Services/AuthenticationService";

class DetailKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            kantorCabang: {},
            role: AuthenticationService.getRole(),
            dataGeneral: {},
            redirect: false
        }

        this.renderDataKantor = this.renderDataKantor.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderDataKantor();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/kantor-cabang",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    auditFormatter(kunjunganAudit){
        if (kunjunganAudit === false){
            return "Belum Pernah"
        }else if (kunjunganAudit === true){
            return "Sudah Pernah"
        }
    }

    async renderDataKantor() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await KantorCabangService.getKantorCabangDetail(this.props.location.state.id);

        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
           kantorCabang: response.data.result,
           dataGeneral: {
            "Nama Point      :": response.data.result.namaKantor,
            "Branch Manager  :": response.data.result.pemilik.nama,
            "Area            :": response.data.result.area,
            "Regional        :": response.data.result.regional,
            "Status          :": response.data.result.status,
            "Kunjungan Audit :": this.auditFormatter(response.data.result.kunjunganAudit)
        }
        }, this.props.contentFinishLoading())
    }

    hapus(id) {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server"); 

        const kantorCabang = {
            id: id
        };
       
        KantorCabangService.deleteKantorCabang(kantorCabang)
            .then(() => this.setRedirect());

        this.props.contentFinishLoading()
    }
   
    subButton() {
        const role = this.state.role;
        const admin = role === "Administrator";
        if (admin) {
            return (
                <div>
                    <NavLink to={{
                        pathname: "/kantor-cabang/ubah",
                        state: {
                            id: this.state.kantorCabang.idKantor,
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
                        modalDesc="Apakah anda yakin untuk menghapus kantor cabang?"
                        onConfirm={() => this.hapus(this.state.kantorCabang.idKantor)}
                        customConfirmText="Ya, Hapus"
                        customCancelText="Batal"
                    >
                        Hapus
                    </SirioWarningButton>
                </div>
            )
        }
        
    }

    // getTitle() {
    //     var title = "Detail Kantor Cabang " + this.state.kantorCabang.namaKantor
    //     return title;
    // }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioDetailPage
                    title="Detail Kantor Cabang"
                    //title={this.getTitle()}
                    data={this.state.dataGeneral}
                    id='id'
                    subButton={this.subButton(this.state.kantorCabang.status)}
                    link="kantor-cabang"
                />
            </>
        );
    }
}

export default withRouter(DetailKantorCabang);