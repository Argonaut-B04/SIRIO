import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";

class DetailKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            kantorCabang: {},
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
                pathname: "/administrator/kantorCabang",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    auditFormatter(kunjunganAudit){
        if (kunjunganAudit == false){
            return "Belum Pernah"
        }else if (kunjunganAudit == true){
            return "Sudah Pernah"
        }
    }

    async renderDataKantor() {
        const response = await KantorCabangService.getKantorCabangDetail(this.props.location.state.id);

        this.setState({
           kantorCabang: response.data.result,
           dataGeneral: {
            "Nama Point      :": response.data.result.namaKantor,
            "Branch Manager  :": response.data.result.pemilik.nama,
            "Area            :": response.data.result.area,
            "Regional        :": response.data.result.regional,
            "Kunjungan Audit :": this.auditFormatter(response.data.result.kunjunganAudit)
        }
        })
    }

    hapus(id) {
        const kantorCabang = {
            id: id
        };
        KantorCabangService.deleteKantorCabang(kantorCabang)
            .then(() => this.setRedirect());
    }
   
    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/administrator/kantorCabang/ubah",
                    state: {
                        id: this.state.kantorCabang.idKantor,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
                <a>     </a>
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

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioDetailPage
                    title="Detail Kantor Cabang"
                    data={this.state.dataGeneral}
                    id='id'
                    subButton={this.subButton()}
                />
            </>
        );
    }
}

export default withRouter(DetailKantorCabang);