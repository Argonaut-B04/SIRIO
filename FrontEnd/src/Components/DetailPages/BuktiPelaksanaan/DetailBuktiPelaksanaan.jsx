import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import BuktiPelaksanaanService from '../../../Services/BuktiPelaksanaanService';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import { NavLink } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import AuthenticationService from "../../../Services/AuthenticationService";

class DetailBuktiPelaksanaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            buktiPelaksanaan: {},
            role: AuthenticationService.getRole(),
            redirect: false
        };
            
        this.renderDataBuktiPelaksanaan = this.renderDataBuktiPelaksanaan.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderDataBuktiPelaksanaan();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/bukti-pelaksanaan",
                state: {
                    approveSuccess: true
                }
            }} />
        }
    };

    async renderDataBuktiPelaksanaan() {
        const response = await BuktiPelaksanaanService.getBuktiPelaksanaan(this.props.location.state.id);

        this.setState({
            buktiPelaksanaan: response.data.result
        })
    }

    data() {
        console.log(this.state.buktiPelaksanaan);
        return {
            "Keterangan:": this.state.buktiPelaksanaan.keterangan,
            "Lampiran:": this.lampiranFormater(this.state.buktiPelaksanaan.lampiran),
            "Feedback:": this.feedbackFormatter(this.state.buktiPelaksanaan.feedback)
        }
    }

    lampiranFormater(lampiran) {
        return (<a href={lampiran}>{lampiran}</a>)
    }

    feedbackFormatter(feedback) {
        if (feedback) {
            return feedback
        } else {
            return "-"
        }
    }

    setuju() {
        const buktiPelaksanaan = {
            status: 2
        };
        BuktiPelaksanaanService.setStatusBukti(this.state.buktiPelaksanaan.id, buktiPelaksanaan)
            .then(() => this.setRedirect());
    }

    buttonUbah(id) {
        return (
            <NavLink to={{
                pathname: "/bukti-pelaksanaan/ubah",
                state: {
                    id: this.state.buktiPelaksanaan.id
                }
            }}>
                <SirioButton
                    purple
                    classes="mx-1"
                >
                    Ubah
                </SirioButton>
            </NavLink>
        )
    }

    buttonSetuju() {
        return (
            <SirioConfirmButton
                purple recommended
                classes="mx-1"
                modalTitle="Apakah anda yakin untuk menyetujui bukti pelaksanaan?"
                onConfirm={() => this.setuju()}
                customConfirmText="Ya, Setujui"
                customCancelText="Batal"
            >
                Setujui
            </SirioConfirmButton>
        )
    }

    buttonTolak(id) {
        return (
            <NavLink to={{
                pathname: "/bukti-pelaksanaan/tolak",
                state: {
                    id: this.state.buktiPelaksanaan.id
                }
            }}>
                <SirioButton
                    purple
                    classes="mx-1"
                >
                    Tolak
                </SirioButton>
            </NavLink>
        )
    }

    subButton() {
        const status = this.state.buktiPelaksanaan.status;
        const menungguPersetujuan = status === 1 
        const diTolak = status === 3;
        const diSetujui = status === 2;
        const role = this.state.role;
        const bm = role === "Branch Manager";
        const nonBm = role === "admin" || role === "Manajer Operational Risk" ||
                                role === "QA Officer Operational Risk" || role === "Super QA Officer Operational Risk";

        if (bm) {
            if (menungguPersetujuan || diTolak) {
                return (
                    <div>
                        {this.buttonUbah(this.state.buktiPelaksanaan.id)}
                    </div>
                );
            } else if (diSetujui) {
                return ("")
            }
        } else if (nonBm) {
            if (menungguPersetujuan) {
                return (
                    <div>
                        {this.buttonSetuju()}
                        {this.buttonTolak(this.state.buktiPelaksanaan.id)}
                    </div>
                );
            } else if (diSetujui || diTolak) {
                return ("")
            }
        }
    };

    render() {
        return (
            <div>
                {this.renderRedirect()}
                <SirioDetailPage
                    title="Detail Bukti Pelaksanaan"
                    link="bukti-pelaksanaan"
                    data={this.data()}
                    id='id'
                    subButton={this.subButton()}
                />
            </div>
        );
    }
}

export default withRouter(DetailBuktiPelaksanaan);