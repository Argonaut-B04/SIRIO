import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDetailPage from '../SirioDetailPage';
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

    async renderDataRencana() {
        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanDetail(this.props.location.state.id);

        this.setState({
           rencanaPemeriksaan: response.data.result,
           dataGeneral: {
            "Nama Rencana     :": response.data.result.namaRencana,
            "Link Pemeriksaan  :": response.data.result.linkMajelis
        }
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
            </>
            
        );
    }
} 

export default withRouter(DetailRencanaPemeriksaan);