import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import KantorCabangService from '../../../Services/KantorCabangService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class DetailKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            kantorCabang: {}
        }

        this.renderDataKantor = this.renderDataKantor.bind(this);
    }

    componentDidMount() {
        this.renderDataKantor();
    }

    async renderDataKantor() {
        const response = await KantorCabangService.getKantorCabangDetail(this.props.location.state.id);

        this.setState({
           kantorCabang: response.data.result
        })
    }

    data() {
        return {
            "Nama Point      :": this.state.kantorCabang.namaKantor,
            "Branch Manager  :": this.state.kantorCabang.pemilik,
            "Area            :": this.state.kantorCabang.area,
            "Regional        :": this.state.kantorCabang.regional,
            "Kunjungan Audit :": this.state.kantorCabang.kunjunganAudit
        };
    }

    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/KantorCabang/ubah",
                    state: {
                        id: this.state.kantorCabang.idKantorCabang,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
                <a>     </a>
                <NavLink to={{
                    pathname: "/KantorCabang/hapus",
                    state: {
                        id: this.state.kantorCabang.idKantorCabang,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Hapus
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    render() {
        return (
            <SirioDetailPage
                title="Detail Kantor Cabang"
                data={this.data()}
                id='id'
                subButton={this.subButton()}
            />
        );
    }
}

export default withRouter(DetailKantorCabang);