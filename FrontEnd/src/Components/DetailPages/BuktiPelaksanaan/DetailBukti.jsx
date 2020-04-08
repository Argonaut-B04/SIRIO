import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService'
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class DetailBukti extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            buktiPelaksanaan: {}
        }

        this.renderDataBukti = this.renderDataBukti.bind(this);
    }

    componentDidMount() {
        this.renderDataBukti();
    }

    async renderDataBukti() {
        const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);

        this.setState({
            buktiPelaksanaan: response.data.result
        })
    }

    data() {
        return {
            "Keterangan": this.state.buktiPelaksanaan.keterangan,
            "Lampiran": this.state.buktiPelaksanaan.lampiran
        }
    }

    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/bukti-pelaksanaan/tambah",
                    state: {
                        id: this.state.buktiPelaksanaan.id,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Ubah Bukti
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    render() {
        return (
            <SirioDetailPage
                title="Detail Bukti Pelaksanaan"
                data={this.data()}
                id='id'
                subButton={this.subButton()}
            />
        );
    }
} 

export default withRouter(DetailBukti);